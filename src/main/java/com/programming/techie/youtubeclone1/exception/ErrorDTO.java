package com.programming.techie.youtubeclone1.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
@Schema(name = "Error", description = "Types of errors")
public class ErrorDTO {

    @Schema(description = "Time, when exception catch", example = "2020-11-18T19:06:41.291Z")
    ZonedDateTime timestamp;

    @Schema(description = "Code", example = "404")
    Integer status;

    @Schema(description = "Status", example = "Not Found")
    String error;

    @Schema(description = "Full name class exception",
            example = "com.programming.techie.youtubeclone1.models.Comment")
    String exception;

    @Schema(description = "Exception description", example = "Entity not found")
    String message;

    @Schema(description = "URL", example = "/api/videos/658b3462b7988017733761b9")
    String path;
}
