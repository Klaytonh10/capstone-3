package org.yearup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EasyshopApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EasyshopApplication.class);
        app.run(args);
    }
}
