package com.example.sagacoordinator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.common.entity")
public class SagaCoordinatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SagaCoordinatorApplication.class, args);
    }

}
