package com.cognizant.socma.ms.tweet.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {

  @GetMapping("/jvi")
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok("Hello JVI");
  }

}
