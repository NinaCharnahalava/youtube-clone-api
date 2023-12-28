package com.programming.techie.youtubeclone1.mapper;

import com.programming.techie.youtubeclone1.dto.VideoDTO;
import com.programming.techie.youtubeclone1.models.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.concurrent.atomic.AtomicInteger;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VideoMapper {

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "videoUrl", source = "entity.videoUrl")
    @Mapping(target = "thumbnailUrl", source = "entity.thumbnailUrl")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "tags", source = "entity.tags")
    @Mapping(target = "videoStatus", source = "entity.videoStatus")
    @Mapping(target = "likesCount", source = "entity.likes")
    @Mapping(target = "dislikesCount", source = "entity.dislikes")
    @Mapping(target = "viewCount", source = "entity.viewCount")
    @Mapping(target = "commentList", source = "entity.commentList")
    VideoDTO toDto(Video entity);

    @Mapping(target = "id", source = "videoDTO.id")
    @Mapping(target = "title", source = "videoDTO.title")
    @Mapping(target = "description", source = "videoDTO.description")
    @Mapping(target = "tags", source = "videoDTO.tags")
    @Mapping(target = "videoStatus", source = "videoDTO.videoStatus")
    @Mapping(target = "thumbnailUrl", source = "videoDTO.thumbnailUrl")
    Video toEntity(VideoDTO videoDTO);

    default Integer map(AtomicInteger value) {
        return value != null ? value.get() : null;
    }

    default AtomicInteger map(Integer value) {
        return value != null ? new AtomicInteger(value) : null;
    }
}
