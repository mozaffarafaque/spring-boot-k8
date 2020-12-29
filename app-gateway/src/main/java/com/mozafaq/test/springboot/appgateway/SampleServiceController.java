package com.mozafaq.test.springboot.appgateway;


import java.util.concurrent.atomic.AtomicLong;

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

    @GetMapping("/status")
    public String getStatus() {
        LOG.info("Request for status query");
        return "OK";
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

}