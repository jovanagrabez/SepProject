package com.sep.kp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class KpApplication {

    public static void main(String[] args) {
        SpringApplication.run(KpApplication.class, args);
    }

}
