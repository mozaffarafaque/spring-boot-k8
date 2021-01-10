package com.mozafaq.test.sparkapp;

import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.*;

public class JobRunnerTest {

    String fileLocation = System.getenv("HOME") + "/codebase/spring-boot-k8/etc/test-spark-file.txt";

    @Test
    public void testMain() {

        System.out.println("File location: " + fileLocation);
        String appId = "app-id-" + UUID.randomUUID().toString();
        System.out.println("Application ID: " + appId);
        JobRunner.main(new String[]{ "local", fileLocation  , appId, "25"});
    }
}