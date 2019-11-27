package com.sep.paypal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "PayPal")
public class PaypalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaypalApplication.class, args);
    }

}
