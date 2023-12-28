package com.programming.techie.youtubeclone1.controllers;

import com.programming.techie.youtubeclone1.dto.CommentDTO;
import com.programming.techie.youtubeclone1.dto.UploadVideoResponse;
import com.programming.techie.youtubeclone1.dto.VideoDTO;
import com.programming.techie.youtubeclone1.exception.ErrorDTO;
import com.programming.techie.youtubeclone1.services.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static java.lang.System.lineSeparator;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@Validated
@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))})
@RequestMapping("/api/videos")
@Tag(name = "Video")
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "API for uploading video")
    @ApiResponse(responseCode = "201", description = "Video is uploaded")
    public UploadVideoResponse uploadVideo(
            @Parameter(description = "File for uploading as video")
            @RequestParam("file")
            MultipartFile file) {
        log.info("Received HTTP request POST /api/videos:file={}", file);
        final UploadVideoResponse response = videoService.uploadVideo(file);
        log.info("Produced HTTP response 201 for POST /api/videos:{}{}", lineSeparator(), response);
        return response;
    }

    @PostMapping("/thumbnail")
    @ResponseStatus(CREATED)
    @Operation(summary = "API for uploading thumbnail")
    @ApiResponse(responseCode = "201", description = "Thumbnail is uploaded")
    public String uploadThumbnail(
            @Parameter(description = "Image for uploading as thumbnail")
            @RequestParam("file")
            MultipartFile file,
            @Parameter(name = "VideoId", example = "658b3462b7988017733761b9")
            @RequestParam("videoId")
            String videoId) {
        log.info("Received HTTP request POST /api/videos/thumbnail:file={}, videoId={}", file, videoId);
        final String response = videoService.uploadThumbnail(file, videoId);
        log.info("Produced HTTP response 201 for POST /api/videos/thumbnail:{}{}", lineSeparator(), response);
        return response;
    }

    @PutMapping
    @ResponseStatus(OK)
    @Operation(summary = "API for saving video")
    @ApiResponse(responseCode = "200", description = "Video is saved")
    public VideoDTO saveVideoDetails(
            @Parameter(description = "Video data")
            @Valid
            @RequestBody
            VideoDTO videoDTO) {
        log.info("Received HTTP request PUT /api/videos:{}{}", lineSeparator(), videoDTO);
        final VideoDTO response = videoService.saveVideoDetails(videoDTO);
        log.info("Produced HTTP response 200 for PUT /api/videos:{}{}", lineSeparator(), response);
        return response;
    }

    @GetMapping("/{videoId}")
    @ResponseStatus(OK)
    @Operation(summary = "API for retrieving video details")
    @ApiResponse(responseCode = "200", description = "Video details are fetched")
    public VideoDTO getVideoDetails(
            @Parameter(name = "VideoId", example = "658b3462b7988017733761b9")
            @PathVariable
            String videoId) {
        log.info("Received HTTP request GET /api/videos/{}", videoId);
        final VideoDTO response = videoService.getVideoDetails(videoId);
        log.info("Produced HTTP 200 response for GET /api/videos/{}:{}{}", videoId, lineSeparator(), response);
        return response;
    }

    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "API for retrieving all videos")
    @ApiResponse(responseCode = "200", description = "All videos have been retrieved")
    public List<VideoDTO> getAllVideos() {
        log.info("Received HTTP request GET /api/videos");
        final List<VideoDTO> response = videoService.getAllVideos();
        log.info("Received HTTP request GET /api/videos:{}{}", lineSeparator(), response);
        return response;
    }

    @PostMapping("/{videoId}/like")
    @ResponseStatus(OK)
    @Operation(summary = "API to like video")
    @ApiResponse(responseCode = "200", description = "Video is liked")
    public VideoDTO likeVideo(
            @Parameter(name = "VideoId", example = "658b3462b7988017733761b9")
            @PathVariable
            String videoId) {
        log.info("Received HTTP request POST /api/videos/{}/like", videoId);
        final VideoDTO response = videoService.likeVideo(videoId);
        log.info("Produced HTTP 200 response for POST /api/videos/{}/like:{}{}", videoId, lineSeparator(), response);
        return response;
    }

    @PostMapping("/{videoId}/dislike")
    @ResponseStatus(OK)
    @Operation(summary = "API to dislike video")
    @ApiResponse(responseCode = "200", description = "Video is disliked")
    public VideoDTO dislikeVideo(
            @Parameter(name = "VideoId", example = "658b3462b7988017733761b9")
            @PathVariable
            String videoId) {
        log.info("Received HTTP request POST /api/videos/{}/dislike", videoId);
        final VideoDTO response = videoService.dislikeVideo(videoId);
        log.info("Produced HTTP 200 response for POST /api/videos/{}/dislike:{}{}", videoId, lineSeparator(), response);
        return response;
    }

    @PostMapping("/{videoId}/comment")
    @ResponseStatus(OK)
    @Operation(summary = "API for adding comment to a video")
    @ApiResponse(responseCode = "200", description = "Added a comment to the video")
    public void addComment(
            @Parameter(name = "VideoId", example = "658b3462b7988017733761b9")
            @PathVariable
            String videoId,
            @Parameter(description = "Comment details")
            @Valid
            @RequestBody
            CommentDTO commentDTO) {
        log.info("Received HTTP request POST /api/videos/{}/comment:{}{}", videoId, lineSeparator(), commentDTO);
        videoService.addComment(videoId, commentDTO);
        log.info("Produced HTTP 200 response for POST /api/videos/{}/comment", videoId);
    }

    @GetMapping("/{videoId}/comments")
    @ResponseStatus(OK)
    @Operation(summary = "API for retrieving all comments associated with a video")
    @ApiResponse(responseCode = "200", description = "All comments associated with a video have been retrieved")
    public List<CommentDTO> getAllComments(
            @Parameter(name = "VideoId", example = "658b3462b7988017733761b9")
            @PathVariable
            String videoId) {
        log.info("Received HTTP request GET /api/videos/{}/comments", videoId);
        final List<CommentDTO> response = videoService.getAllComments(videoId);
        log.info("Received HTTP request GET /api/videos/{}/comments:{}{}", videoId, lineSeparator(), response);
        return response;
    }
}
