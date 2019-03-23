package com.cognizant.socma.ms.tweet.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cognizant.socma.ms.tweet.dto.TweetDto;
import com.cognizant.socma.ms.tweet.services.TweetService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tweet")
@RequiredArgsConstructor
public class TweetController {

  private final TweetService tweetService;

  @GetMapping("/test")
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok("Hello tweet");
  }

  @GetMapping("/create")
  public ResponseEntity<String> create() {

    final TweetDto tweetDto = new TweetDto();
    final String message = "Mon tweet #Java #CPP";
    tweetDto.setMessage(message);
    tweetDto.setUserId(1L);
    tweetService.createTweet(tweetDto);
    return ResponseEntity.ok("created");
  }

}
