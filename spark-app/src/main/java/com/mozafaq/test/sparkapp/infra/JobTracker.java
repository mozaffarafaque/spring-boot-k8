package com.mozafaq.test.sparkapp.infra;

import com.mozafaq.test.springboot.utilslib.CommandExec;
import com.mozafaq.test.springboot.utilslib.CommandExecutor;
import com.mozafaq.test.springboot.utilslib.CommandResponse;
import org.apache.spark.scheduler.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JobTracker extends SparkListener {

    public static String DEFAULT_SERVER = "http://spark-master-server:9013/progress-event";
    public static String JOBID_VM = "spark.job.id";
    public static String SERVER_END_POINT = "event.notification.endpoint";

    private String jobId = "Default-Unknown";
    private String remoteEndpoint = null;
    public void onStageCompleted(final SparkListenerStageCompleted stageCompleted) {
        runCommand("onStageCompleted", stageCompleted);
    }

    public void onStageSubmitted(final SparkListenerStageSubmitted stageSubmitted) {
        runCommand("onStageSubmitted", stageSubmitted);
    }

    public void onTaskStart(final SparkListenerTaskStart taskStart) {
        runCommand("onTaskStart", taskStart);
    }

    public void onTaskGettingResult(final SparkListenerTaskGettingResult taskGettingResult) {
        runCommand("onTaskGettingResult", taskGettingResult);
    }

    public void onTaskEnd(final SparkListenerTaskEnd taskEnd) {
        runCommand("onTaskEnd", taskEnd);
    }

    public void onJobStart(final SparkListenerJobStart jobStart) {
        runCommand("onJobStart", jobStart);
    }

    public void onJobEnd(final SparkListenerJobEnd jobEnd) {
        runCommand("onJobEnd", jobEnd);
    }

    public void onEnvironmentUpdate(final SparkListenerEnvironmentUpdate environmentUpdate) {
        runCommand("onEnvironmentUpdate", environmentUpdate);
    }

    public void onBlockManagerAdded(final SparkListenerBlockManagerAdded blockManagerAdded) {
        runCommand("onBlockManagerAdded", blockManagerAdded);
    }

    public void onBlockManagerRemoved(final SparkListenerBlockManagerRemoved blockManagerRemoved) {
        runCommand("onBlockManagerRemoved", blockManagerRemoved);
    }

    public void onUnpersistRDD(final SparkListenerUnpersistRDD unpersistRDD) {
        runCommand("onUnpersistRDD", unpersistRDD);
    }

    public void onApplicationStart(final SparkListenerApplicationStart applicationStart) {
        runCommand("onApplicationStart", applicationStart);
    }

    public void onApplicationEnd(final SparkListenerApplicationEnd applicationEnd) {
        runCommand("onApplicationEnd", applicationEnd);
    }

    public void onExecutorMetricsUpdate(final SparkListenerExecutorMetricsUpdate executorMetricsUpdate) {
        runCommand("onExecutorMetricsUpdate", executorMetricsUpdate);
    }

    public void onStageExecutorMetrics(final SparkListenerStageExecutorMetrics executorMetrics) {
        runCommand("onStageExecutorMetrics", executorMetrics);
    }

    public void onExecutorAdded(final SparkListenerExecutorAdded executorAdded) {
        runCommand("onExecutorAdded", executorAdded);
    }

    public void onExecutorRemoved(final SparkListenerExecutorRemoved executorRemoved) {
        runCommand("onExecutorRemoved", executorRemoved);
    }

    public void onExecutorBlacklisted(final SparkListenerExecutorBlacklisted executorBlacklisted) {
        runCommand("onExecutorBlacklisted", executorBlacklisted);
    }

    public void onExecutorBlacklistedForStage(final SparkListenerExecutorBlacklistedForStage executorBlacklistedForStage) {
        runCommand("onExecutorBlacklistedForStage", executorBlacklistedForStage);
    }

    public void onNodeBlacklistedForStage(final SparkListenerNodeBlacklistedForStage nodeBlacklistedForStage) {
        runCommand("onNodeBlacklistedForStage", nodeBlacklistedForStage);
    }

    public void onExecutorUnblacklisted(final SparkListenerExecutorUnblacklisted executorUnblacklisted) {
        runCommand("onExecutorUnblacklisted", executorUnblacklisted);
    }

    public void onNodeBlacklisted(final SparkListenerNodeBlacklisted nodeBlacklisted) {
        runCommand("onNodeBlacklisted", nodeBlacklisted);
    }

    public void onNodeUnblacklisted(final SparkListenerNodeUnblacklisted nodeUnblacklisted) {
        runCommand("onNodeUnblacklisted", nodeUnblacklisted);
    }

    public void onBlockUpdated(final SparkListenerBlockUpdated blockUpdated) {
        runCommand("onBlockUpdated", blockUpdated);
    }

    public void onSpeculativeTaskSubmitted(final SparkListenerSpeculativeTaskSubmitted speculativeTask) {
        runCommand("onSpeculativeTaskSubmitted", speculativeTask);
    }

    public void onOtherEvent(final SparkListenerEvent event) {
        runCommand("onOtherEvent", event);
    }

    private void runCommand(String event, Object progress) {

        System.out.println("INFO ["  + new Date().toInstant() + "][" + event + "] " + progress);

        if (remoteEndpoint == null) {
            System.out.println("Not executing command... end point not configured");
            return;
        }

        CommandExec exec = new CommandExec();
        String[] args = new String[]{"curl", "-XGET", "--max-time", "15",
                remoteEndpoint + "?" +
                        "jobId=" + encode(this.jobId) +
                        "&eventName=" + encode(event) +
                        "&progress=" + encode(progress.toString())
        };
        //int statusCode = exec.executeCommand( args, 10_000);

        CommandExecutor commandExecutor = new CommandExecutor();
        CommandResponse response = commandExecutor.execute(args, null, 10_000);

        int statusCode = response.getExitStatus();
        System.out.println("INFO ["  + new Date().toInstant() + "][" + event + "] " + progress + ", Status code: " + statusCode);
    }

    public JobTracker() {
        this(System.getProperty(JOBID_VM), System.getProperty(SERVER_END_POINT));
    }


    private String encode(String value) {
        try {
            String encoded = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
            return  encoded.length() < 1024 ? encoded : "TooLargePayload";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "encodeFailure";
        }
    }

    public JobTracker(String jobId, String remoteEndpoint) {

        if (remoteEndpoint !=  null) {
            this.remoteEndpoint = remoteEndpoint;
        } else {
            this.remoteEndpoint = DEFAULT_SERVER;
        }
        this.jobId = jobId;
    }
}
