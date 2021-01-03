package com.mozafaq.test.springboot.appgateway;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.mozafaq.test.springboot.utilslib.HttpClient;
import com.mozafaq.test.springboot.utilslib.TestClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class SampleServiceController {

    private static final String template = "Hello, %s!";
    private static final Logger LOG = LoggerFactory.getLogger(SampleServiceController.class);

    private final AtomicLong counter = new AtomicLong();
    private final AtomicLong concurrencyCounter = new AtomicLong();


    private static String COMPUTE_WORKER_HOST = String.format("http://%s", System.getenv("WORKER_COMPUTE_HOST"));
    private static int COMPUTE_WORKER_PORT = Integer.parseInt(System.getenv("WORKER_COMPUTE_PORT"));
    private static String COMPUTE_WORKER_URL = String.format("%s:%s", COMPUTE_WORKER_HOST, String.valueOf(COMPUTE_WORKER_PORT));


    private static String CONTROLLER_WORKER_HOST = String.format("http://%s", System.getenv("WORKER_CONTROLLER_HOST"));
    private static int CONTROLLER_WORKER_PORT = Integer.parseInt(System.getenv("WORKER_CONTROLLER_PORT"));
    private static String CONTROLLER_WORKER_URL = String.format("%s:%s", CONTROLLER_WORKER_HOST, String.valueOf(CONTROLLER_WORKER_PORT));

    @GetMapping("/app-gateway/app-name")
    public String appNamePath() {
        LOG.info("Request for Application name with Path");
        return appName();
    }

    @GetMapping("/app-gateway/status")
    public String getStatusPath() {
        LOG.info("Request for status query with Path");
        return getStatus();
    }

    @GetMapping("/app-gateway/calculate-fibonacci")
    public long calculateFibonacciPath(@RequestParam(value = "number", defaultValue = "1") int number) {
        LOG.info("Request for calculating the fibonacci of (with Path) " + number );
        return calculateFibonacci(number);
    }

    @GetMapping("/status")
    public String getStatus() {
        LOG.info("Request for status query");
        return "OK";
    }

    @GetMapping("/app-name")
    public String appName() {
        LOG.info("Request for Application name");
        return "app-gateway\n";
    }

    @GetMapping("/sample-get")
    public Response sampleGet(@RequestParam(value = "name", defaultValue = "UnknownRequest") String name) {
        LOG.info("Request : " + name + " (" + new TestClass().randomString() + ")");
        return new Response(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/custom-waited-get")
    public Response sampleGetDelayed(@RequestParam(value = "waitInSeconds", defaultValue = "10") int waitTime,
                                     @RequestParam(value = "requestId", defaultValue = "DEFAULT") String requestId) {
        try {
            long concurrent =  concurrencyCounter.incrementAndGet();
            LOG.info("Start: Request serving " + requestId + ", Concurrent: " + concurrent);
            try {
                Thread.sleep(waitTime * 1000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            LOG.info("Completed: Request serving " + requestId);
            return new Response(counter.incrementAndGet(), String.format("Wait time %s second(s)", String.valueOf(waitTime)));

        } finally {

            concurrencyCounter.decrementAndGet();
        }
    }

    @PostMapping(path= "/sample-post", consumes = "application/json", produces = "application/json")
    public PostResponse<String> samplePost(@RequestBody PostRequest request) {
        return new PostResponse<>(counter.incrementAndGet(), request.getPayload());
    }

    @PostMapping(path= "/sample-post-5s", consumes = "application/json", produces = "application/json")
    public PostResponse<String> timeTaken5s(@RequestBody PostRequest request) {
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        return new PostResponse<>(counter.incrementAndGet(), request.getPayload());
    }

    @PostMapping(path= "/sample-post-30s", consumes = "application/json", produces = "application/json")
    public PostResponse<String> timeTaken30s(@RequestBody PostRequest request) {
        try {
            Thread.sleep(30*1000);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        return new PostResponse<>(counter.incrementAndGet(), request.getPayload());
    }

    @GetMapping("/calculate-fibonacci")
    public long calculateFibonacci(@RequestParam(value = "number", defaultValue = "1") int number) {
        LOG.info("Request for calculating the fibonacci of " + number );

        HttpClient client = new HttpClient(COMPUTE_WORKER_URL + "/calculate-fibonacci");
        String value = client.performGet(Collections.singletonMap("number", String.valueOf(number)));
        return Long.parseLong(value);
    }

    @GetMapping("/sum")
    public long calculateSum(@RequestParam(name = "num1") long num1,
                             @RequestParam(name = "num2") long num2) {

        LOG.info("Start: Request sum calculation " + num1 + " and " + num2);
        HttpClient client = new HttpClient(COMPUTE_WORKER_URL + "/sum");
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("num1", String.valueOf(num1));
        map.put("num2", String.valueOf(num2));
        String value = client.performGet(map);
        return Long.parseLong(value);
    }

    @GetMapping("/worker-controller/log-app-access")
    public String logAppAccessPath() {
        LOG.info("Start: Request for app log access with path");
        return logAppAccess();
    }

    @GetMapping("/log-app-access")
    public String logAppAccess() {
        LOG.info("Start: Request for app log access");
        HttpClient client = new HttpClient(CONTROLLER_WORKER_URL + "/log-app-access");
        String value = client.performGet(Collections.emptyMap());
        return value;
    }
}