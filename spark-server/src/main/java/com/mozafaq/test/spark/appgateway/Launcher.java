package com.mozafaq.test.spark.appgateway;

import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Launcher {

    private static final String SPARK_HOME = "/opt/spark";
    private static final String APP_JAR = "/app/spark-app.jar";

    public int launchSparkJob(LauncherRequest launcherRequest) {

        Objects.requireNonNull(launcherRequest, "Launcher request");
        Objects.requireNonNull(launcherRequest.getMaster(), "Master cannot be null");
        Objects.requireNonNull(launcherRequest.getJobId(), "Job Id cannot be null");
        Objects.requireNonNull(launcherRequest.getDeployMode(), "Deploy mode cannot be null");

        Path out;
        Path err;
        try {
            out = Files.createTempFile("stdout-" + launcherRequest.getJobId(), ".txt");
            err = Files.createTempFile("stderr-" + launcherRequest.getJobId(), ".txt");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        SparkLauncher launcher = new SparkLauncher()
               // .setSparkHome(SPARK_HOME)
                .setAppResource(APP_JAR)
                .setDeployMode(launcherRequest.getDeployMode())
                .setMaster(launcherRequest.getMaster())
                .setAppName(launcherRequest.getJobId())
                .setMainClass(launcherRequest.getMain())
                .redirectError(err.toFile())
                .redirectOutput(out.toFile())
                .addAppArgs(launcherRequest.getArguments()
                        .toArray(new String[launcherRequest.getArguments().size()]));

        launcherRequest.getConfigurations()
                .entrySet()
                .stream()
                .forEach(e -> launcher.setConf(e.getKey(), e.getValue()));

        Process process = null;
        try {

             //SparkAppHandle sparkAppHandle = launcher.startApplication();
             process  =launcher.launch();
            boolean status = process.waitFor(launcherRequest.getTimeoutMillis(), TimeUnit.MILLISECONDS);
             return status ? 0 : 1;
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
