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

    private static String FILE_PATH = System.getenv("PERSISTENT_PATH");

    @GetMapping("/worker-controller/app-name")
    public String appNamePath() {
        LOG.info("Request for Application name with Path");
        return appName();
    }

    @GetMapping("/worker-controller/status")
    public String getStatusPath() {
        LOG.info("Request for status query with Path");
        return getStatus();
    }

    @GetMapping("/log-app-access")
    public String logAppAccessPath() {
        LOG.info("Request for app access with Path");
        return logAppAccess();
    }


    @GetMapping("/worker-controller/log-app-access")
    public String logAppAccess() {
        String uuid = UUID.randomUUID().toString();
        try {
            Files.createFile(Path.of(FILE_PATH + "/" + uuid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uuid;
    }


    @GetMapping("/worker-controller/calculate-fibonacci")
    public long calculateFibonacciPath(@RequestParam(value = "number", defaultValue = "1") int number) {
        LOG.info("Request for calculating the fibonacci of (with Path) " + number );
        return calculateFibonacci(number);
    }

        @GetMapping("/app-name")
    public String appName() {
        LOG.info("Request for Application name");
        return "app-worker-controller\n";
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