package com.programming.techie.youtubeclone1.services;

import com.programming.techie.youtubeclone1.dto.CommentDTO;
import com.programming.techie.youtubeclone1.dto.UploadVideoResponse;
import com.programming.techie.youtubeclone1.dto.VideoDTO;
import com.programming.techie.youtubeclone1.mapper.CommentMapper;
import com.programming.techie.youtubeclone1.mapper.VideoMapper;
import com.programming.techie.youtubeclone1.models.Comment;
import com.programming.techie.youtubeclone1.models.Video;
import com.programming.techie.youtubeclone1.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final UserService userService;
    private final VideoMapper videoMapper;
    private final CommentMapper commentMapper;

    public UploadVideoResponse uploadVideo(MultipartFile multipartFile) {
        String videoUrl = s3Service.uploadFile(multipartFile);
        var video = new Video();
        video.setVideoUrl(videoUrl);
        var savedVideo = videoRepository.save(video);

        return new UploadVideoResponse(savedVideo.getId(), savedVideo.getVideoUrl());
    }

    public Video findVideoById(String videoId) {
        return videoRepository.findById(videoId).orElseThrow(() -> new IllegalArgumentException(
                "Cannot find video by id = " + videoId));
    }

    public String uploadThumbnail(MultipartFile multipartFile, String videoId) {
        var currentVideo = findVideoById(videoId);
        String thumbnailUrl = s3Service.uploadFile(multipartFile);
        currentVideo.setThumbnailUrl(thumbnailUrl);
        videoRepository.save(currentVideo);

        return thumbnailUrl;
    }

    public VideoDTO saveVideoDetails(VideoDTO videoDTO) {
        var currentVideo = findVideoById(videoDTO.getId());
        currentVideo = videoMapper.toEntity(videoDTO);
        videoRepository.save(currentVideo);

        return videoMapper.toDto(currentVideo);
    }

    public VideoDTO getVideoDetails(String videoId) {
        Video currentVideo = findVideoById(videoId);
        increaseVideoCount(currentVideo);
        userService.addVideoToHistory(videoId);

        return videoMapper.toDto(currentVideo);
    }

    private void increaseVideoCount(Video currentVideo) {
        currentVideo.incrementViewCount();
        videoRepository.save(currentVideo);
    }

    public VideoDTO likeVideo(String videoId) {
        Video currentVideo = findVideoById(videoId);

        if(userService.isVideoLiked(videoId)) {
            currentVideo.decrementLikes();
            userService.removeFromLikedVideos(videoId);
        } else if (userService.isVideoDisliked(videoId)) {
            currentVideo.decrementDislikes();
            userService.removeFromDislikedVideos(videoId);
            currentVideo.incrementLikes();
            userService.addToLikedVideos(videoId);
        } else {
            currentVideo.incrementLikes();
            userService.addToLikedVideos(videoId);
        }

        videoRepository.save(currentVideo);

        return videoMapper.toDto(currentVideo);
    }

    public VideoDTO dislikeVideo(String videoId) {
        Video currentVideo = findVideoById(videoId);

        if(userService.isVideoDisliked(videoId)) {
            currentVideo.decrementDislikes();
            userService.removeFromDislikedVideos(videoId);
        } else if (userService.isVideoLiked(videoId)) {
            currentVideo.decrementLikes();
            userService.removeFromLikedVideos(videoId);
            currentVideo.incrementDislikes();
            userService.addToDislikedVideos(videoId);
        } else {
            currentVideo.incrementDislikes();
            userService.addToDislikedVideos(videoId);
        }

        videoRepository.save(currentVideo);

        return videoMapper.toDto(currentVideo);
    }

    public List<VideoDTO> getAllVideos() {
        return videoRepository.findAll().stream()
                .map(video -> videoMapper.toDto(video))
                .toList();
    }

    public void addComment(String videoId, CommentDTO commentDTO) {
        Video currentVideo = findVideoById(videoId);
        Comment addedComment = new Comment();
        addedComment = commentMapper.toEntity(commentDTO);
        currentVideo.addComment(addedComment);

        videoRepository.save(currentVideo);
    }

    public List<CommentDTO> getAllComments(String videoId) {
        Video currentVideo = findVideoById(videoId);
        List<Comment> commentsList = currentVideo.getCommentList();

        return commentsList.stream().map(this::mapToCommentDTO).toList();
    }

    private CommentDTO mapToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO = commentMapper.toDto(comment);

        return commentDTO;
    }
}
