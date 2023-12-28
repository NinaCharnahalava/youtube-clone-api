package com.programming.techie.youtubeclone1.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Document(value = "Video")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Video {

    @Id
    String id;
    String title;
    String description;
    String userId;
    AtomicInteger likes = new AtomicInteger(0);
    AtomicInteger dislikes = new AtomicInteger(0);
    Set<String> tags;
    String videoUrl;
    VideoStatus videoStatus;
    AtomicInteger viewCount = new AtomicInteger(0);
    String thumbnailUrl;
    List<Comment> commentList = new CopyOnWriteArrayList<>();

    public void incrementLikes() {
        this.likes.incrementAndGet();
    }

    public void decrementLikes() {
        this.likes.decrementAndGet();
    }

    public void incrementDislikes() {
        this.dislikes.incrementAndGet();
    }

    public void decrementDislikes() {
        this.dislikes.decrementAndGet();
    }

    public void incrementViewCount() {
        this.viewCount.incrementAndGet();
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }
}
