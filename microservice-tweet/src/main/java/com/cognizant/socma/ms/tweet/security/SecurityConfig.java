package com.cognizant.socma.ms.tweet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.NullSecurityContextRepository;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomAuthenticationProvider authProvider;

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    log.info("*** configure security");
    auth.authenticationProvider(authProvider);
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {

    // @formatter:off
    http.securityContext()
      .securityContextRepository(new NullSecurityContextRepository())
      .and()
      .servletApi()
      .and()
      .authorizeRequests().mvcMatchers("/api/**").authenticated()
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .cors()
      .and()
      .formLogin().disable()
      .httpBasic()
      .and()
      .logout().disable()
      .requestCache().disable()
      .rememberMe().disable()
      .anonymous().disable()
      .csrf().disable();
    // @formatter:on
    // http.authorizeRequests().mvcMatchers("/api/**").authenticated().and().httpBasic();
  }
}
