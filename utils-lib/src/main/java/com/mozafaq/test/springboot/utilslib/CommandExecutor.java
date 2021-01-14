package com.mozafaq.test.springboot.utilslib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(CommandExecutor.class);

    private static final int RETRY_COUNT_CMD_DONE = 15;

    public CommandExecutor() {
    }

    public CommandResponse execute(String[] command,  Map<String, String> envs, long timeoutMillis) {

        int exitValue = -1;
        long startTime = System.currentTimeMillis();

        CommandResponse response = new CommandResponse();

        try {
            ProcessBuilder pb = getProcessBuilder(command);
            if (envs != null && !envs.isEmpty()) {
                Map<String, String> environments = pb.environment() ;
                environments.putAll(envs);
            }

            long timeOut = timeoutMillis;
            LOG.info("Command is executing '{}' with timeout {} mill second(s).",
                    Arrays.stream(command).collect(Collectors.joining(" ")), timeOut);

            Process p = pb.start();
            StreamCollector stdOutCollector = new StreamCollector((e) -> {}, new InputStreamReader(p.getInputStream()), true, true);
            stdOutCollector.start();

            StreamCollector stdErrCollector = null;

                stdErrCollector = new StreamCollector(e -> {} , new InputStreamReader(p.getErrorStream()), false, true);
                stdErrCollector.start();

            // Run with little more timeout
            boolean isSuccess = p.waitFor(timeOut + 100, TimeUnit.MILLISECONDS);
            if (isSuccess) {
                exitValue = p.exitValue();
            } else {
                exitValue = 1;
            }

            int i = 0;
            while (i < RETRY_COUNT_CMD_DONE && ((stdErrCollector == null || !stdErrCollector.isFinished()) || !stdOutCollector.isFinished())) {
                Thread.sleep(100);
                i++;
            }

            if (stdOutCollector.isAlive()) {
                // As precautionary measure lets stop the thread
                stdOutCollector.interrupt();
            }

            if (stdErrCollector != null && stdErrCollector.isAlive()) {
                // As precautionary measure lets stop the thread
                stdErrCollector.interrupt();
            }
        } catch (final IOException | InterruptedException e) {
            LOG.error("Error while executing the process ", e);
            exitValue = 1;
        }
        response.setExitStatus(exitValue);
        return response;
    }

    private ProcessBuilder getProcessBuilder(String[] cmds) {
        return new ProcessBuilder(cmds);
    }
}

class StreamCollector extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(StreamCollector.class);
    private Consumer<String> outputConsumer;
    private InputStreamReader isr;
    private String threadName;
    private boolean isStdOut;
    private boolean isFinished;
    private boolean addStdInLog;

    StreamCollector(Consumer<String> outputConsumer, InputStreamReader isr, boolean isStdOut,
                           boolean addStdInLog) {
        super.setDaemon(true);
        this.outputConsumer = outputConsumer;
        this.isr = isr;
        this.isStdOut = isStdOut;
        this.threadName = Thread.currentThread().getName();
        this.addStdInLog = addStdInLog;
        setFinished(false);
    }

    @Override
    public void run() {
        Thread.currentThread().setName(threadName + (isStdOut ? "-cout" : "-cerr"));
        try {
            emitStream();
        } catch (Exception e) {
            LOG.error("Error while executing the thread ", e);
        } finally {
            setFinished(true);
        }
    }

    private void emitStream() throws IOException {

        try (BufferedReader br = new BufferedReader(isr)) {

            String line;

            while ((line = br.readLine()) != null) {
                if (addStdInLog) {
                    if (isStdOut) {
                        System.out.println(line);
                        LOG.info("[STDOUT] " + line);
                    } else {
                        System.err.println(line);
                        LOG.warn("[STDERR] " + line);
                    }
                }
                Arrays.stream(line.split("\n")).forEach(outputConsumer);
            }
        }
    }

    private synchronized void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    synchronized boolean isFinished() {
        return isFinished;
    }

}

