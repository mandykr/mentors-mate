package com.mentors.mentorsmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MentorsMateApplication {

    public static void main(String[] args) {
        SpringApplication.run(MentorsMateApplication.class, args);
    }

}
