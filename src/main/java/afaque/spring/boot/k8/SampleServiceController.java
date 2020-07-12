package afaque.spring.boot.k8;


import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

@RestController
public class SampleServiceController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/sample-request")
    public Response sampleGet(@RequestParam(value = "name", defaultValue = "Unknown") String name) {
        return new Response(counter.incrementAndGet(), String.format(template, name));
    }

    @PostMapping(path= "/sample-post", consumes = "application/json", produces = "application/json")
    public PostResponse<String> samplePost(@RequestBody PostRequest request) {
        return new PostResponse<>(counter.incrementAndGet(), request.getPayload());
    }
}