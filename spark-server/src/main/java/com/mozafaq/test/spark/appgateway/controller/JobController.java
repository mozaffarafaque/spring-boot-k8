package com.mozafaq.test.spark.appgateway.controller;

import com.mozafaq.test.spark.appgateway.Launcher;
import com.mozafaq.test.spark.appgateway.LauncherRequest;
import com.mozafaq.test.springboot.utilslib.CommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


        int count1 = 4;
        String[] args = getBaseArguments(count1, clientId, deployMode, masterEndPoint, "com.mozafaq.test.sparkapp.PiCalculator");
        args[args.length - count1 + 0] = clientId;
        args[args.length - count1 + 1] = String.valueOf(count);
        args[args.length - count1 + 2] = ">>";
        args[args.length - count1 + 3] = "/tmp/job-stdout.txt";

        CommandExecutor exec = new CommandExecutor();

        LauncherRequest request = getBaseLaunchRequest(clientId, deployMode, masterEndPoint, "com.mozafaq.test.sparkapp.PiCalculator");
        request.addArguments(clientId).addArguments(String.valueOf(count));

        Launcher launcher = new Launcher();
        int status = launcher.launchSparkJob(request);
        LOG.info("Status for " + clientId + " " + status);
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


    private String[] getBaseArguments(int additionalParamCount,
                                      String clientId,
                                      String deployMode,
                                      String endpoint,
                                      String mainClass) {
        String notificationConf = "spark.driver.extraJavaOptions=-Dspark.job.id=" + clientId;

        String[] argsTemp = new String[] {
                SPARK_HOME + "/bin/spark-submit",
                "--master",
                endpoint,
                "--deploy-mode",
                deployMode,
                "--name",
                clientId,
                "--class",
                mainClass,
                "--conf",
                "spark.executor.instances=2",
                "--conf",
                notificationConf,
                "--conf",
                "spark.extraListeners=com.mozafaq.test.sparkapp.infra.JobTracker",
                JAR_LOCATION,
        };

        String[] arguments = new String[argsTemp.length + additionalParamCount];

        for (int i = 0; i < argsTemp.length; i++) {
            arguments[i] = argsTemp[i];
        }
        return arguments;
    }


    @GetMapping("/run-spark-word-count")
    public int runSparkWordCount(@RequestParam(name = "fileLocation") String fileLocation,
                                 @RequestParam(name = "clientId") String clientId,
                                 @RequestParam(name = "deployMode") String deployMode,
                                 @RequestParam(name = "masterEndpoint") String masterEndpoint) {

        int count = 6;
        String[] args = getBaseArguments(count,
                clientId,
                deployMode,
                masterEndpoint,
                "com.mozafaq.test.sparkapp.JobRunner");

        args[args.length - count + 0] = masterEndpoint;
        args[args.length - count + 1] = fileLocation;
        args[args.length - count + 2] = clientId;
        args[args.length - count + 3] = "25";
        args[args.length - count + 4] = ">>";
        args[args.length - count + 5] = "/tmp/job-stdout.txt";


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
