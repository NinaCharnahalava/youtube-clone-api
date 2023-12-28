package com.programming.techie.youtubeclone1.dto;

import com.programming.techie.youtubeclone1.models.Comment;
import com.programming.techie.youtubeclone1.models.VideoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(name = "VideoDTO to create")
public class VideoDTO {

    @Schema(description = "ID of the created video", example = "658b3462b7988017733761b9")
    String id;

    @NotBlank(message = "Video title cannot be blank")
    @Schema(description = "Title of the created video", example = "Sunset in mountains")
    String title;

    @Schema(description = "Description of the created video",
            example = "Incredible sunset time among the Italian Alps")
    String description;

    @Schema(description = "Tags of the created video", example = "Sunset, Mountains, Alps")
    Set<String> tags;

    @Schema(description = "Video URL",
            example = "https://s3.amazonaws.com/example_bucket/example_video.mp4")
    String videoUrl;

    @Schema(description = "Status of the created video", example = "Private/Public/Unlisted")
    VideoStatus videoStatus;

    @Schema(description = "Thumbnail URL of the video",
            example = "https://example.com/thumbnails/video123.jpg")
    String thumbnailUrl;

    @Schema(description = "Count of video likes", example = "20 likes")
    Integer likesCount;

    @Schema(description = "Count of video dislikes", example = "5 dislikes")
    Integer dislikesCount;

    @Schema(description = "Count of video views", example = "100 views")
    Integer viewCount;

    @Schema(description = "Video comments",
            example = "[{text:Great video!,authorId:user123},{text:Awesome!,authorId:user456}]")
    List<Comment> commentList;
}
