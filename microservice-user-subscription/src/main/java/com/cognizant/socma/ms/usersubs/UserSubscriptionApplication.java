package com.cognizant.socma.ms.usersubs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserSubscriptionApplication {

  public static void main(final String[] args) {
    SpringApplication.run(UserSubscriptionApplication.class, args);
  }

}
