package com.programming.techie.youtubeclone1.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Document(value = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    String id;
    String sub;
    String nickname;
    String name;
    String emailAddress;
    Set<String> subscribedToUsers = ConcurrentHashMap.newKeySet();
    Set<String> subscribers = ConcurrentHashMap.newKeySet();
    Set<String> videoHistory = ConcurrentHashMap.newKeySet();
    Set<String> likedVideos = ConcurrentHashMap.newKeySet();
    Set<String> dislikedVideos = ConcurrentHashMap.newKeySet();

    public void addToLikedVideos(String videoId) {
        this.likedVideos.add(videoId);
    }

    public void removeFromLikedVideos(String videoId) {
        this.likedVideos.remove(videoId);
    }

    public void addToDislikedVideos(String videoId) {
        this.dislikedVideos.add(videoId);
    }

    public void removeFromDislikedVideos(String videoId) {
        this.dislikedVideos.remove(videoId);
    }

    public void addVideoToHistory(String videoId) {
        this.videoHistory.add(videoId);
    }

    public void addToSubscribedToUsersList(String userId) {
        this.subscribedToUsers.add(userId);
    }

    public void addToSubscribers(String userId) {
        this.subscribers.add(userId);
    }

    public void removeFromSubscribedToUsersList(String userId) {
        this.subscribedToUsers.remove(userId);
    }

    public void removeFromSubscribers(String userId) {
        this.subscribers.remove(userId);
    }
}
