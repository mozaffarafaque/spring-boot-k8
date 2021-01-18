package com.mozafaq.test.spark.appgateway.controller;

import com.mozafaq.test.spark.appgateway.Launcher;
import com.mozafaq.test.spark.appgateway.LauncherRequest;
import com.mozafaq.test.spark.appgateway.model.SparkJobRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class JobController {

    private static final String SPARK_HOME = "/opt/spark";
    private static final String JAR_LOCATION = "/app/spark-app.jar";
    private static final Logger LOG = LoggerFactory.getLogger(JobController.class);

    @Value("${spark.server.notification.url}")
    private String url;

    @GetMapping("/app-name")
    public String appName() {
        LOG.info("Request for Application name");
        return "spark-job-controller\n";
    }

    @GetMapping("/status")
    public String getStatus() {
        LOG.info("Request for status query");
        return "OK";
    }

    @GetMapping("/run-spark-pi-job")
    public int runSparkPiJob(@RequestParam(name = "count") int count,
                             @RequestParam(name = "clientId") String clientId,
                             @RequestParam(name = "deployMode") String deployMode,
                             @RequestParam(name = "masterEndpoint") String masterEndPoint) {

        LauncherRequest request = getBaseLaunchRequest(clientId, deployMode, masterEndPoint, "com.mozafaq.test.sparkapp.PiCalculator");
        request.addArguments(clientId).addArguments(String.valueOf(count));

        Launcher launcher = new Launcher();
        int status = launcher.launchSparkJob(request);
        LOG.info("Status for " + clientId + " " + status);
        return status;
    }

    @PostMapping("/launch-spark-app")
    public int launchSparkApp(@RequestBody SparkJobRequest sparkJobRequest) {

        LOG.info("Request: " + sparkJobRequest);

        LauncherRequest request = getBaseLaunchRequest(
                sparkJobRequest.getJobId(),
                sparkJobRequest.getDeployMode(),
                sparkJobRequest.getMasterEndpoint(),
                Optional.ofNullable(sparkJobRequest.getMainClass()).orElse("com.mozafaq.test.sparkapp.JobRunner"));

        request.setTimeoutMillis(sparkJobRequest.getTimeoutMillis());
        for (String arg: sparkJobRequest.getArguments()) {
            request.addArguments(arg);
        }

        for (Map.Entry<String, String> e: sparkJobRequest.getConfs().entrySet()) {
            request.addConfigurations(e.getKey(), e.getValue());
        }

        Launcher launcher = new Launcher();
        int status = launcher.launchSparkJob(request);
        return status;
    }

    private LauncherRequest getBaseLaunchRequest(String clientId,
                                                 String deployMode,
                                                 String endpoint,
                                                 String mainClass) {
        return new LauncherRequest()
                .setDeployMode(deployMode)
                .setMain(mainClass)
                .setJobId(clientId)
                .setMaster(endpoint)
                .addConfigurations("spark.executor.instances", "2")
                .addConfigurations("spark.extraListeners", "com.mozafaq.test.sparkapp.infra.JobTracker")
                .addConfigurations("spark.driver.extraJavaOptions", "-Dspark.job.id=" + clientId);
    }

    @GetMapping("/run-spark-word-count")
    public int runSparkWordCount(@RequestParam(name = "fileLocation") String fileLocation,
                                 @RequestParam(name = "clientId") String clientId,
                                 @RequestParam(name = "deployMode") String deployMode,
                                 @RequestParam(name = "masterEndpoint") String masterEndpoint) {

        LauncherRequest request = getBaseLaunchRequest(clientId, deployMode, masterEndpoint, "com.mozafaq.test.sparkapp.JobRunner");
        request.addArguments(masterEndpoint)
                .addArguments(fileLocation)
                .addArguments(clientId)
                .addArguments("25");
        Launcher launcher = new Launcher();
        int status = launcher.launchSparkJob(request);
        LOG.info("Status for " + clientId + " " + status);
        return status;
    }

    @GetMapping("/progress-event")
    public void jobProgressEvent(@RequestParam(name = "jobId") String jobId,
                                 @RequestParam(name = "eventName") String eventName,
                                 @RequestParam(name = "progress")String progress) {

        LOG.info("Job Id: " + jobId + ", EventName: " + eventName +
                ", Progress Status: " + progress);
    }
}
