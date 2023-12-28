package com.programming.techie.youtubeclone1.services;

import com.programming.techie.youtubeclone1.models.User;
import com.programming.techie.youtubeclone1.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        String sub = ((Jwt)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getClaim("sub");

        return userRepository.findUserBySub(sub).orElseThrow(() -> (new IllegalArgumentException(
                "Can't find the user with sub = " + sub
        )));
    }

    public void addToLikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addToLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    public void addToDislikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addToDislikedVideos(videoId);
        userRepository.save(currentUser);
    }

    public void removeFromLikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    public void removeFromDislikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromDislikedVideos(videoId);
        userRepository.save(currentUser);
    }

    public boolean isVideoLiked(String videoId) {
        User currentUser = getCurrentUser();

        return currentUser.getLikedVideos().stream().anyMatch(
                likedVideos -> likedVideos.equals(videoId));
    }

    public boolean isVideoDisliked(String videoId) {
        User currentUser = getCurrentUser();

        return currentUser.getDislikedVideos().stream().anyMatch(
                id -> id.equals(videoId));
    }

    public void addVideoToHistory(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addVideoToHistory(videoId);
        userRepository.save(currentUser);
    }

    public void subscribeToUser(String userId) {
        User currentUser = getCurrentUser();
        currentUser.addToSubscribedToUsersList(userId);

        User userToSubscribed = getUserById(userId);
        userToSubscribed.addToSubscribers(currentUser.getId());

        userRepository.save(currentUser);
        userRepository.save(userToSubscribed);
    }

    public void unsubscribeToUser(String userId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromSubscribedToUsersList(userId);

        User userToUnsubscribed = getUserById(userId);
        userToUnsubscribed.removeFromSubscribers(currentUser.getId());

        userRepository.save(currentUser);
        userRepository.save(userToUnsubscribed);
    }

    public Set<String> getUserVideoHistory(String userId) {
        User currentUser = getUserById(userId);

        return currentUser.getVideoHistory();
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(
                String.format("Cannot find user to subscribe by user id = %s", userId)
        ));
    }
}
