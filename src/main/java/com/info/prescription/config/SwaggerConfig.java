package com.info.prescription.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*@Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
                .apiInfo(apiInfo()).select().paths(postPaths()).build();
    }*/

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).
                select()
                .apis(RequestHandlerSelectors.basePackage("com.info.prescription.controller.api.expose"))
                .paths(PathSelectors.any())
//                .paths(PathSelectors.ant("/api/*"))
                .build();
    }

    private Predicate<String> postPaths() {
        return or(regex("/api/v1/prescription.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Spring Security API")
                .description("Tofazzal Hossain's API reference for developers")
                .termsOfServiceUrl("http://manik.mmanik.com")
                .contact("manik.mmanik@gmail.com").license("JavaInUse License")
                .licenseUrl("manik.mmanik@gmail.com").version("1.0").build();
    }

}