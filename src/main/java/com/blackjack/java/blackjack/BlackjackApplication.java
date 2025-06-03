package com.blackjack.java.blackjack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableReactiveMongoRepositories(basePackages = "com.blackjack.java.blackjack.repository")
@EnableR2dbcRepositories(basePackages = "com.blackjack.java.blackjack.repository")
@SpringBootApplication
public class BlackjackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlackjackApplication.class, args);
    }
}