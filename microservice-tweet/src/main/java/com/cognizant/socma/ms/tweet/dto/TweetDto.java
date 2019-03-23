package com.cognizant.socma.ms.tweet.dto;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TweetDto {

  @NotNull
  @JsonProperty(required = true)
  private Long userId;

  @NotNull
  @JsonProperty(required = true)
  private String message;

}

