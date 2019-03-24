package com.cognizant.socma.ms.tweet.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "user-service")
public interface UserClient {

  @GetMapping(path = "/user/test")
  public ResponseEntity<String> sayHello();

}
