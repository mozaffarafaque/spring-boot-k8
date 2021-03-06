package com.mozafaq.test.springboot.appgateway;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import com.mozafaq.test.springboot.utilslib.TestClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class ComputeController {

    private static final String template = "Hello, %s!";
    private static final Logger LOG = LoggerFactory.getLogger(ComputeController.class);

    private final AtomicLong counter = new AtomicLong();
    private final AtomicLong concurrencyCounter = new AtomicLong();


    @GetMapping("/worker-compute/app-name")
    public String appNamePath() {
        LOG.info("Request for Application name with Path");
        return appName();
    }

    @GetMapping("/worker-compute/status")
    public String getStatusPath() {
        LOG.info("Request for status query with Path");
        return getStatus();
    }

    @GetMapping("/worker-compute/calculate-fibonacci")
    public long calculateFibonacciPath(@RequestParam(value = "number", defaultValue = "1") int number) {
        LOG.info("Request for calculating the fibonacci of (with Path) " + number );
        return calculateFibonacci(number);
    }


    @GetMapping("/app-name")
    public String appName() {
        LOG.info("Request for Application name");
        return "app-worker-compute\n";
    }

    @GetMapping("/status")
    public String getStatus() {
        LOG.info("Request for status query");
        return "OK";
    }

    @GetMapping("/calculate-fibonacci")
    public long calculateFibonacci(@RequestParam(value = "number", defaultValue = "1") int number) {
        LOG.info("Request for calculating the fibonacci of " + number );
        if (number < 3) {
            return 1;
        }
        long f1 = 1, f2 = 1;

        for (int i = 3; i <= number; i++) {
           long f = f1 + f2;
           f1 = f2;
           f2 = f;
        }

        return f2;
    }

    @GetMapping("/sum")
    public long calculateSum(@RequestParam(name = "num1") long num1,
                             @RequestParam(name = "num2") long num2) {

            LOG.info("Start: Request sum calculation " + num1 + " and " + num2);
            return num1+ num2;
    }

}