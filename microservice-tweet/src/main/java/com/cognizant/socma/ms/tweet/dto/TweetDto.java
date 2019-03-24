package com.cognizant.socma.ms.tweet.dto;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Builder
public class TweetDto {

  @NotNull
  @JsonProperty(required = true)
  private Long userId;

  @NotNull
  @JsonProperty(required = true)
  private String message;

}

