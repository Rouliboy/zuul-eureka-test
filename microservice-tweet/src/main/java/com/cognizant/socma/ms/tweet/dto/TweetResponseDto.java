package com.cognizant.socma.ms.tweet.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetResponseDto {

  private Long userId;

  private Instant creationDateTime;

  private String message;

}

