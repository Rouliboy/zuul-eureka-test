package com.cognizant.socma.ms.tweet.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cognizant.socma.shared.swagger.DefaultSwaggerConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends DefaultSwaggerConfiguration {

  @Bean
  public Docket api() {
    return getDocket();
  }

  @Override
  public ApiInfo getApiInfo() {
    return new ApiInfoBuilder().title("SOCMA - Tweet service").build();
  }

}
