package com.cognizant.socma.ms.tweet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TweetUserServiceCallException extends RuntimeException {

  private static final long serialVersionUID = 3066786092753245869L;

  public TweetUserServiceCallException(final String message, final Throwable t) {
    super(message, t);
  }

}
