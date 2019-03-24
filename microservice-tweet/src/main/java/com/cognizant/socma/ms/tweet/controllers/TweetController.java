package com.cognizant.socma.ms.tweet.controllers;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cognizant.socma.ms.tweet.dto.TweetDto;
import com.cognizant.socma.ms.tweet.dto.TweetResponseDto;
import com.cognizant.socma.ms.tweet.entities.Tweet;
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

  @GetMapping("/create/{hashTag}")
  public ResponseEntity<String> create(@PathVariable final String hashTag) {

    final TweetDto tweetDto = new TweetDto();
    final String message = "Mon tweet #" + hashTag;
    tweetDto.setMessage(message);
    tweetDto.setUserId(1L);
    tweetService.createTweet(tweetDto);
    return ResponseEntity.ok("created");
  }

  @GetMapping("/{hashTag}")
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
