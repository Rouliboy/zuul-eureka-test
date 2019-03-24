package com.cognizant.socma.ms.tweet.security;

import java.util.ArrayList;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

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
      return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
    } else {
      return null;
    }
  }

  @Override
  public boolean supports(final Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }

  private boolean shouldAuthenticateAgainstThirdPartySystem() {
    return true;
  }
}
