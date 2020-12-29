/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.mozafaq.test.springboot.appgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication application =  new SpringApplication(Application.class);
        if (args.length == 1) {
            application.setDefaultProperties(Collections.singletonMap("server.port", args[0]));
        }

        application.run(args);
    }
}
