package com.cognizant.socma.ms.tweet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TweetApplication {

  public static void main(final String[] args) {
    SpringApplication.run(TweetApplication.class, args);
  }

}