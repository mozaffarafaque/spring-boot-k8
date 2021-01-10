package com.mozafaq.test.springboot.utilslib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class CommandExec {


    private static final Logger LOG = LoggerFactory.getLogger(CommandExec.class);

    public int executeCommand(String[] args, long waitTimeMills) {
        try {
            String message = "Command: " + Arrays.toString(args);
            System.out.println("[INFO @ " + new Date() + "] Starting " + message);
            LOG.info("Starting: " + message);
            Process p = Runtime.getRuntime().exec(args);
            int i = p.waitFor();
            LOG.info("[INFO @ " + new Date() + "] Completed " + message + ", status code: " + i);
            System.out.println("Completed " + message + ", status code: " + i);
            return i;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
