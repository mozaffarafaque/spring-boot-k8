package com.mozafaq.test.spark.appgateway.controller;

import com.mozafaq.test.springboot.utilslib.CommandExec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    private static final String SPARK_HOME = "/opt/spark";
    private static final String JAR_LOCATION = "/app/spark-app.jar";
    private static final Logger LOG = LoggerFactory.getLogger(JobController.class);

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

        CommandExec exec = new CommandExec();
        String[] args = new String[] {
                SPARK_HOME + "/bin/spark-submit",
                "--master",
                masterEndPoint,
                "--deploy-mode",
                deployMode,
                "--name",
                clientId,
                "--class",
                "com.mozafaq.test.sparkapp.PiCalculator",
                "--conf",
                "spark.executor.instances=2",
                JAR_LOCATION,
                clientId,
                String.valueOf(count),
                ">>",
                "/tmp/job-stdout.txt"
        };
        return  exec.executeCommand(args, 20_000);
    }

    @GetMapping("/run-spark-word-count")
    public int runSparkWordCount(@RequestParam(name = "fileLocation") String fileLocation,
                                 @RequestParam(name = "clientId") String clientId,
                                 @RequestParam(name = "deployMode") String deployMode,
                                 @RequestParam(name = "masterEndpoint") String masterEndPoint) {

        CommandExec exec = new CommandExec();
        String[] args = new String[] {
              SPARK_HOME + "/bin/spark-submit",
              "--master",
              masterEndPoint,
              "--deploy-mode",
                deployMode,
              "--name",
              clientId,
              "--class",
              "com.mozafaq.test.sparkapp.JobRunner",
              "--conf",
              "spark.executor.instances=2",
              JAR_LOCATION,
              masterEndPoint,
              fileLocation,
              clientId,
              "25",
                ">>",
                "/tmp/job-stdout.txt"
        };
        return exec.executeCommand(args, 20_000);
    }


    @GetMapping("/progress-event")
    public void jobProgressEvent(@RequestParam(name = "eventName") String eventName,
                                 @RequestParam(name = "progress")String progress) {

        LOG.info("EventName: " + eventName + ", Progress Status: " + progress);
    }
}
