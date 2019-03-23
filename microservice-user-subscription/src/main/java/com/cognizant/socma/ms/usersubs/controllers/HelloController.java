package com.cognizant.socma.ms.usersubs.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class HelloController {

  @GetMapping("/test")
  public ResponseEntity<String> sayHello() {
    log.info("Say hello to user");
    return ResponseEntity.ok("Hello user");
  }

}
