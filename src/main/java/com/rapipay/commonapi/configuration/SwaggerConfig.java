package com.rapipay.commonapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("sessionRefNo")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        java.util.List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.rapipay.commonapi.controllers"))

                .build().globalOperationParameters(aParameters);
    }

}
