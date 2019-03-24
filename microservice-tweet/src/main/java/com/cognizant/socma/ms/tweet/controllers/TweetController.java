package com.cognizant.socma.ms.tweet.controllers;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cognizant.socma.ms.tweet.dto.TweetDto;
import com.cognizant.socma.ms.tweet.dto.TweetResponseDto;
import com.cognizant.socma.ms.tweet.entities.Tweet;
import com.cognizant.socma.ms.tweet.services.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
@Slf4j
public class TweetController {

  private final TweetService tweetService;

  @GetMapping("/test")
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok("Hello tweet");
  }

  @PostMapping
  public ResponseEntity<String> create(@Valid @RequestBody final TweetDto dto) {

    log.info("Create tweet {}", dto);
    tweetService.createTweet(dto);
    return ResponseEntity.ok("created");
  }

  @GetMapping("/create/{hashTag}")
  public ResponseEntity<String> create(@PathVariable final String hashTag) {


    final String message = "Mon tweet #" + hashTag;
    final TweetDto tweetDto = new TweetDto(1L, message);
    tweetService.createTweet(tweetDto);
    return ResponseEntity.ok("created");
  }

  @GetMapping("/hashtag/{hashTag}")
  public ResponseEntity<List<TweetResponseDto>> getTweetsWithHashTag(
      @PathVariable final String hashTag) {

    // @formatter:off
    final List<TweetResponseDto> tweets = tweetService.getTweetsWithHashTag(hashTag)
        .stream()
        .map(toResponseDto())
        .collect(Collectors.toList());
    // @formatter:on
    return ResponseEntity.ok(tweets);
  }

  private Function<Tweet, TweetResponseDto> toResponseDto() {
    return t -> {
      return new TweetResponseDto(t.getId(), t.getCreationDateTime(), t.getMessage());
    };
  }

}
