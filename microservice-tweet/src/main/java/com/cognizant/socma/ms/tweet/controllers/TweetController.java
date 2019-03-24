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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cognizant.socma.ms.tweet.dto.TweetCreationDto;
import com.cognizant.socma.ms.tweet.dto.TweetResponseDto;
import com.cognizant.socma.ms.tweet.entities.Tweet;
import com.cognizant.socma.ms.tweet.services.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/tweets")
@RequiredArgsConstructor
@Slf4j
public class TweetController {

  private final TweetService tweetService;

  @GetMapping("/test")
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok("Hello tweet");
  }

  @PostMapping
  public ResponseEntity<String> create(@Valid @RequestBody final TweetCreationDto dto) {

    log.info("Create tweet {}", dto);
    tweetService.createTweet(dto);
    return ResponseEntity.ok("created");
  }

  @GetMapping
  public ResponseEntity<List<TweetResponseDto>> getTweets(
      @RequestParam(required = true) final List<Long> userIds) {

    log.info("Get tweets from userIds : {}", userIds);

    // @formatter:off
    final List<TweetResponseDto> tweets = tweetService.getTweetsFromUsers(userIds)
        .stream()
        .map(toResponseDto())
        .collect(Collectors.toList());
    // @formatter:on
    return ResponseEntity.ok(tweets);
  }

  @GetMapping("/hashtag/{hashtag}")
  public ResponseEntity<List<TweetResponseDto>> getTweetsWithHashtag(
      @PathVariable final String hashtag) {

    log.info("Searching tweets with hashtag {}", hashtag);

    // @formatter:off
    final List<TweetResponseDto> tweets = tweetService.getTweetsWithHashtag(hashtag)
        .stream()
        .map(toResponseDto())
        .collect(Collectors.toList());
    // @formatter:on
    return ResponseEntity.ok(tweets);
  }

  private Function<Tweet, TweetResponseDto> toResponseDto() {
    return t -> {
      return new TweetResponseDto(t.getUserId(), t.getCreationDateTime(), t.getMessage());
    };
  }

}
