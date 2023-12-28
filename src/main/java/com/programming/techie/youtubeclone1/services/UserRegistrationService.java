package com.programming.techie.youtubeclone1.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programming.techie.youtubeclone1.dto.UserInfoDTO;
import com.programming.techie.youtubeclone1.models.User;
import com.programming.techie.youtubeclone1.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private String userInfoEndpoint = "https://youtube-clone-test-auth.eu.auth0.com/userinfo";
    private final UserRepository userRepository;

    public String registerUser(String tokenValue) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(userInfoEndpoint))
                .setHeader("Authorization", String.format("Bearer %s", tokenValue))
                 .build();

        HttpClient httpClient = HttpClient.newBuilder()
                 .version(HttpClient.Version.HTTP_2)
                 .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            UserInfoDTO userInfoDTO = objectMapper.readValue(responseBody, UserInfoDTO.class);

            Optional<User> userBySubject = userRepository.findUserBySub(userInfoDTO.getSub());

            if(userBySubject.isPresent()) {
                return userBySubject.get().getId();
            }
            else {
                User user = new User();
                user.setSub(userInfoDTO.getSub());
                user.setNickname(userInfoDTO.getNickname());
                user.setName(userInfoDTO.getName());
                user.setEmailAddress(userInfoDTO.getEmail());

                return userRepository.save(user).getId();
            }
        }
        catch (IOException | InterruptedException exception) {
            throw new RuntimeException("Exception occurred while registering user!", exception);
        }
    }
}
