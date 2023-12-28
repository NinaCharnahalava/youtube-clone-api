package com.programming.techie.youtubeclone1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
public class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private VideoData videoData;

    private AuthConfig authConfig;

    public VideoControllerTest() throws IOException {
        var mapper = new ObjectMapper(new YAMLFactory());
        var config = getClass().getResourceAsStream("/application.yml");
        var readValue = mapper.readValue(config, Map.class);
        var settingsMap = ((Map)readValue.get("spring")).get("authConfig");

        authConfig = mapper.convertValue(settingsMap, AuthConfig.class);
    }

    @Test
    void testUploadVideoEndpoint() throws Exception {
        ClassPathResource resource = new ClassPathResource("/data/sunrise.mp4");
        MockMultipartFile file = new MockMultipartFile("file", "/data/sunrise.mp4", MediaType.MULTIPART_FORM_DATA_VALUE, resource.getInputStream());
        final String accessToken = getAccessToken();

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/api/videos")
                        .file(file)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.videoId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.videoUrl").exists());
    }

    @Test
    void testUploadThumbnailEndpoint() {
        ClassPathResource resource = new ClassPathResource("/data/Sunrise_thumbnail.png");
        MockMultipartFile file = null;

        try {
            file = new MockMultipartFile("file", "Sunrise_thumbnail.png", MediaType.IMAGE_PNG_VALUE, resource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            String videoId = getVideoData().getVideoId();
            final String accessToken = getAccessToken();
            mockMvc.perform(MockMvcRequestBuilders
                            .multipart("/api/videos/thumbnail")
                            .file(file)
                            .header("Authorization", "Bearer " + accessToken)
                            .param("videoId", videoId))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSaveVideoDetailsEndpoint() throws Exception {
        final String accessToken = getAccessToken();
        final VideoData videoData = getVideoData();

        VideoShortDTO videoShortDTO = new VideoShortDTO();
        videoShortDTO.setId(videoData.getVideoId());
        videoShortDTO.setTitle("Test Video");
        videoShortDTO.setDescription("This is a test video");
        videoShortDTO.setVideoUrl(videoData.getVideoUrl());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/videos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(toJsonString(videoShortDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(videoData.getVideoId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Video"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This is a test video"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.videoUrl").value(videoData.getVideoUrl()));
    }

    private String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{" +
                        "\"client_id\":\"%s\"," +
                        "\"client_secret\":\"%s\"," +
                        "\"audience\":\"%s\"," +
                        "\"grant_type\":\"%s\"" +
                        "}",
                authConfig.clientId, authConfig.clientSecret, authConfig.audience, authConfig.grantType);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = new RestTemplate().postForEntity(authConfig.authUrl, request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode responseNode = objectMapper.readTree(response.getBody());
            if (response.getStatusCode() == HttpStatus.OK) {
                final String accessToken = responseNode.get("access_token").asText();
                return accessToken;
            } else {
                throw new RuntimeException("Failed to retrieve Access Token");
            }
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    private VideoData getVideoData() throws Exception {
        ClassPathResource resource = new ClassPathResource("/data/sunrise.mp4");
        MockMultipartFile file = new MockMultipartFile("file", "/data/sunrise.mp4",
                MediaType.MULTIPART_FORM_DATA_VALUE, resource.getInputStream());
        final String accessToken = getAccessToken();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/api/videos")
                        .file(file)
                        .header("Authorization", "Bearer " + accessToken))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        videoData = new ObjectMapper().readValue(content, VideoData.class);
        return videoData;
    }

    private static String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
