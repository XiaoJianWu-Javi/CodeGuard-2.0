package es.tfg.codeguard.web.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("CodeGuard© API")
                        .description("API documentation for CodeGuard©. The best app to make java exercises.\nTotally not stealed idea from CodeWars.\n\nAny similarity with reality is just a coincidence.")
                        .version("v0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }

    @Bean
    public GroupedOpenApi publicUserApi() {
        return GroupedOpenApi
            .builder()
            .group("user")
            .pathsToMatch("/user/**")
            .build();
    }

    @Bean
    public GroupedOpenApi publicAdminApi() {
        return GroupedOpenApi
            .builder()
            .group("admin")
            .pathsToMatch("/admin/**")
            .build();
    }

    @Bean
    public GroupedOpenApi publicLoginApi() {
        return GroupedOpenApi
            .builder()
            .group("login")
            .pathsToMatch("/login/**")
            .build();
    }

    @Bean
    public GroupedOpenApi publicRegisterApi() {
        return GroupedOpenApi
            .builder()
            .group("register")
            .pathsToMatch("/register/**")
            .build();
    }

    @Bean
    public GroupedOpenApi publicCompilerApi() {
        return GroupedOpenApi
                .builder()
                .group("compiler")
                .pathsToMatch("/compiler/**")
                .build();
    }

}
