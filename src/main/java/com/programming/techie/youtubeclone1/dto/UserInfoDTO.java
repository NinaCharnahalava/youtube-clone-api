package com.programming.techie.youtubeclone1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(name = "UserInfoDTO registered user information")
public class UserInfoDTO {

    @Schema(description = "ID of the registered user", example = "658b3462b7988017733761b9")
    String id;

    @JsonProperty("sub")
    @Schema(description = "Subject Identifier of the registered user",
            example = "ILXqCQCC4ytGahEQhgW3JhuPWjsZFBwq@clients")
    String sub;

    @JsonProperty("nickName")
    @Schema(description = "Nickname of the registered user", example = "ninaCh")
    String nickname;

    @JsonProperty("name")
    @Schema(description = "Name of the registered user", example = "Nina Charnahalava")
    String name;

    @JsonProperty("picture")
    @Schema(description = "Avatar of the registered user", example = "https://example.com/user123/avatar.jpg")
    String picture;

    @Schema(description = "Email of the registered user", example = "charnahalava.n@gmail.com")
    String email;

    @Schema(description = "Indicates whether the user's email address is verified", example = "true")
    String emailVerified;
}
