package com.programming.techie.youtubeclone1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(name = "Message that appears when an error occurs")
public class ErrorMessage {

    @Schema(description = "Text message describing the error", example = "This is an error message")
    String message;

    public static ErrorMessage from(final String message) {
        return new ErrorMessage(message);
    }
}
