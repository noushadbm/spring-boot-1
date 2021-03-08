package com.rayshan.gitinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class GitInfoApplication {

    public static void main(String[] args) {
        // Register a Flogger back-end factory for Log4j
        System.setProperty(
                "flogger.backend_factory",
                "com.google.common.flogger.backend.slf4j.Slf4jBackendFactory#getInstance");
        SpringApplication.run(GitInfoApplication.class, args);
    }
}
