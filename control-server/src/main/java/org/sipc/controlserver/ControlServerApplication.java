package org.sipc.controlserver;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class ControlServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ControlServerApplication.class, args);
    }
}