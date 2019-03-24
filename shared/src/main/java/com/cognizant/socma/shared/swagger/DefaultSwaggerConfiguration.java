package com.cognizant.socma.shared.swagger;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.google.common.collect.ImmutableSet;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Default Swagger configuration.
 *
 */
public abstract class DefaultSwaggerConfiguration implements WebMvcConfigurer {

  private static final String ENTITE_INVALIDE = "Invalid entity";
  private static final String NON_TROUVE = "Not found";

  public Docket getDocket() {
    final Docket docket =
        new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo())
            .globalOperationParameters(Arrays.asList(new ParameterBuilder().name("Authorization")
                .description("Basic authorization").modelRef(new ModelRef("string"))
                .parameterType("header").required(true).build()))
            .consumes(ImmutableSet.of(MediaType.APPLICATION_JSON_VALUE))
            .produces(ImmutableSet.of(MediaType.APPLICATION_JSON_VALUE))
            .genericModelSubstitutes(ResponseEntity.class)
            .directModelSubstitute(BigDecimal.class, Double.class);

    addResponseMessages(docket);

    return publicApiSelectorBuilder(docket.select()).build();
  }

  public ApiSelectorBuilder publicApiSelectorBuilder(final ApiSelectorBuilder apiSelectorBuilder) {
    requireNonNull(apiSelectorBuilder);
    return apiSelectorBuilder.apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.regex("/api/.*"));
  }

  public void addResponseMessages(final Docket docket) {
    requireNonNull(docket);
    docket.useDefaultResponseMessages(false)
        .globalResponseMessage(RequestMethod.GET,
            customResponses(commonResponses(), toErrorResponse(HttpStatus.NOT_FOUND, NON_TROUVE)))
        .globalResponseMessage(RequestMethod.POST,
            customResponses(commonResponses(),
                toErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ENTITE_INVALIDE)))
        .globalResponseMessage(RequestMethod.PUT,
            customResponses(commonResponses(), toErrorResponse(HttpStatus.NOT_FOUND, NON_TROUVE),
                toErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ENTITE_INVALIDE)))
        .globalResponseMessage(RequestMethod.PATCH,
            customResponses(commonResponses(), toErrorResponse(HttpStatus.NOT_FOUND, NON_TROUVE),
                toErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ENTITE_INVALIDE)))
        .globalResponseMessage(RequestMethod.DELETE,
            customResponses(commonResponses(), toErrorResponse(HttpStatus.NOT_FOUND, NON_TROUVE),
                toErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ENTITE_INVALIDE)));
  }

  public List<ResponseMessage> commonResponses() {
    return Arrays.asList(toErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorized"),
        toErrorResponse(HttpStatus.FORBIDDEN, "Forbidden"),
        toErrorResponse(HttpStatus.BAD_REQUEST, "Bad request"),
        toErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
  }

  public List<ResponseMessage> customResponses(final List<ResponseMessage> common,
      final ResponseMessage... custom) {
    final List<ResponseMessage> commonResponses = ofNullable(common).orElse(emptyList());
    return Stream.concat(commonResponses.stream(), Stream.of(custom)).collect(toList());
  }

  public ResponseMessage toErrorResponse(final HttpStatus status, final String message) {
    return new ResponseMessageBuilder().code(status.value()).message(message).build();
  }

  protected abstract ApiInfo getApiInfo();

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("**/swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("**/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}
