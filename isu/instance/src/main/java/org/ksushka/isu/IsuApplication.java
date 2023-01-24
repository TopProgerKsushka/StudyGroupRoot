package org.ksushka.isu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class IsuApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsuApplication.class, args);
    }

}
