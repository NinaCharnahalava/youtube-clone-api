package com.programming.techie.youtubeclone1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response after uploading a video")
public class UploadVideoResponse {

    @Schema(description = "ID of the uploaded video", example = "658b3462b7988017733761b9")
    String videoId;

    @Schema(description = "URL of the uploaded video",
            example = "https://s3.amazonaws.com/example_bucket/example_video.mp4")
    String videoUrl;
}
