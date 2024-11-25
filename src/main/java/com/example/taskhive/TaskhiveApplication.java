package com.example.taskhive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TaskhiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskhiveApplication.class, args);
    }

}
