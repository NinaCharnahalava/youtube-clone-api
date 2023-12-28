package com.programming.techie.youtubeclone1.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Youtube Clone",
        version = "0.0.1",
        description = "This is youtube-clone1 service"))
public class SwaggerConfiguration {
}
