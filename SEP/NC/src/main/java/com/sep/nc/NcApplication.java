package com.sep.nc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class NcApplication {

    public static void main(String[] args) {
        SpringApplication.run(NcApplication.class, args);
    }

}
