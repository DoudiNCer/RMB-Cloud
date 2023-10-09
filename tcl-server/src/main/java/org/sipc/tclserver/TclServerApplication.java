package org.sipc.tclserver;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class TclServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TclServerApplication.class, args);
    }
}