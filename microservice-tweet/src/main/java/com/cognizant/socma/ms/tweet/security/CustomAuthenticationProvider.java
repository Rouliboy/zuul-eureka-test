package com.cognizant.socma.ms.tweet.security;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import com.cognizant.socma.ms.tweet.client.UserClient;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private UserClient userClient;

  @Override
  public Authentication authenticate(final Authentication authentication)
      throws AuthenticationException {

    log.info("Authentication in CustomAuthenticationProvider");

    final String name = authentication.getName();
    final String password = authentication.getCredentials().toString();

    log.info("name={}, password = {}", name, password);

    if (shouldAuthenticateAgainstThirdPartySystem()) {

      log.info("shouldAuthenticateAgainstThirdPartySystem");

      // use the credentials
      // and authenticate against the third-party system
      if (isValidUser(name, password)) {
        return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
      }
    }
    return null;
  }

  @Override
  public boolean supports(final Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }

  private boolean shouldAuthenticateAgainstThirdPartySystem() {
    return true;
  }

  private boolean isValidUser(final String userName, final String password) {

    boolean result = false;
    try {
      final ResponseEntity<String> validateUserResponse = userClient.sayHello();
      log.info("validateUserResponse status = {}", validateUserResponse.getStatusCode());
      result = validateUserResponse.getStatusCode() == HttpStatus.OK;
    } catch (final FeignException e) {
      log.error("Error when validating user on user endpoint", e);
      result = false;
    }
    return result;

  }
}
