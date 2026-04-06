package com.cafe.cafeMood.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(BEARER_SCHEME_NAME))
                .components(new Components().addSecuritySchemes(BEARER_SCHEME_NAME, new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }

    private Info apiInfo() {
        return new Info()
                .title("CafeMood API")
                .description("CafeMood API 문서")
                .version("v1.0.0")
                .contact(new Contact()
                        .name("CafeMood"));
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user-api")
                .pathsToMatch("/cafe/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi ownerApi() {
        return GroupedOpenApi.builder()
                .group("owner-api")
                .pathsToMatch("/cafe/owner/**")
                .build();
    }

    @Bean
    public GroupedOpenApi reviewApi() {
        return GroupedOpenApi.builder()
                .group("review-api")
                .pathsToMatch("/cafe/reviews/**")
                .build();
    }


    @Bean
    public GroupedOpenApi menuApi(){
        return GroupedOpenApi.builder()
                .group("menu-api")
                .pathsToMatch("/cafe/menus/**")
                .build();
    }

    @Bean
    public GroupedOpenApi tagApi(){
        return GroupedOpenApi.builder()
                .group("tag-api")
                .pathsToMatch("/cafe/tags/**")
                .build();
    }

    @Bean
    public GroupedOpenApi searchApi(){
        return GroupedOpenApi.builder()
                .group("search-api")
                .pathsToMatch("/cafe/search/**")
                .build();
    }
}
