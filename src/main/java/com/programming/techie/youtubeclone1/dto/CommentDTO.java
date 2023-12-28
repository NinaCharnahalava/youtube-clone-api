package com.programming.techie.youtubeclone1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(name = "CommentDTO to create")
public class CommentDTO {

    @NotBlank(message = "Comment text cannot be blank")
    @Schema(description = "Text of the comment", example = "Oh, great video")
    String commentText;

    @NotNull(message = "Author id can't be null")
    @Schema(description = "Author id of the comment", example = "658b3462b7988017733761b9")
    String authorId;
}
