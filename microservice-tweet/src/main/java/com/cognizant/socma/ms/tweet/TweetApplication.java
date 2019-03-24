package com.cognizant.socma.ms.tweet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import com.cognizant.socma.shared.cors.EnableCORS;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCORS
@EnableFeignClients
public class TweetApplication {

  public static void main(final String[] args) {
    SpringApplication.run(TweetApplication.class, args);
  }

}
