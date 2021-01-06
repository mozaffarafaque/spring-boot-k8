package com.mozafaq.test.spark.appgateway;

import com.mozafaq.test.spark.appgateway.job.WordCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class SparkServiceController {

    private static final String template = "Hello, %s!";
    private static final Logger LOG = LoggerFactory.getLogger(SparkServiceController.class);

    @GetMapping("/status")
    public String getStatus() {
        LOG.info("Request for status query");
        return "OK";
    }

    @GetMapping("/app-name")
    public String appName() {
        LOG.info("Request for Application name");
        return "spark-server\n";
    }

    @GetMapping("/submit-spark-job")
    @PostMapping("/submit-spark-job")
    public SparkJobResponse submitJob(@RequestParam(value = "request") SparkJobRequest sparkJobRequest) {

        WordCount wordCount = new WordCount();
        wordCount.runJob(sparkJobRequest.getTopN(), sparkJobRequest.getTextFileLocation(), sparkJobRequest.getClientId());

        SparkJobResponse  response = new SparkJobResponse();
        response.setStatus("STARTED");
        return response;
    }

    @GetMapping("/job-status")
    @PostMapping("/job-status")
    public SparkJobResponse getJobStatus(@RequestParam(value = "request") JobStatusRequest jobStatusRequest) {
        return null;
    }
}