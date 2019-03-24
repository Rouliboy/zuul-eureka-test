package com.cognizant.socma.shared.cors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(prefix = "cognizant.socma.cors", name = "enabled", havingValue = "true",
    matchIfMissing = true)
public class CORSConfiguration {

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {

      @Override
      public void addCorsMappings(final CorsRegistry registry) {

        // @formatter:off
        registry.addMapping("/**")
          .allowedHeaders("*")
          .allowedMethods("*")
          .allowedOrigins("*");
        // @formatter:on
      }
    };
  }
}
