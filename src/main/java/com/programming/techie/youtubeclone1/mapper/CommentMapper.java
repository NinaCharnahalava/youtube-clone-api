package com.programming.techie.youtubeclone1.mapper;

import com.programming.techie.youtubeclone1.dto.CommentDTO;
import com.programming.techie.youtubeclone1.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    @Mapping(target = "commentText", source = "entity.text")
    @Mapping(target = "authorId", source = "entity.authorId")
    CommentDTO toDto(Comment entity);

    @Mapping(target = "text", source = "commentDTO.commentText")
    @Mapping(target = "authorId", source = "commentDTO.authorId")
    Comment toEntity(CommentDTO commentDTO);
}
