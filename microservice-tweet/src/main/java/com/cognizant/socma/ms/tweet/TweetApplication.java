package com.cognizant.socma.ms.tweet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import com.cognizant.socma.shared.cors.EnableCORS;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCORS
public class TweetApplication {

  public static void main(final String[] args) {
    SpringApplication.run(TweetApplication.class, args);
  }

}
