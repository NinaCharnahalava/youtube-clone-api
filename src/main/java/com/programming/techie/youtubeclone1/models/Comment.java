package com.programming.techie.youtubeclone1.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {

    @Id
    String id;
    String text;
    String authorId;
    Integer likeCount;
    Integer dislikeCount;
}
