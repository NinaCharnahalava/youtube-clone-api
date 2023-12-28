package com.programming.techie.youtubeclone1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.programming.techie.youtubeclone1.services.UserRegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRegistrationService userRegistrationService;

    private AuthConfig authConfig;

    public UserControllerTest() throws IOException {
        var mapper = new ObjectMapper(new YAMLFactory());
        var config = getClass().getResourceAsStream("/application.yml");
        var readValue = mapper.readValue(config, Map.class);
        var settingsMap = ((Map)readValue.get("spring")).get("authConfig");

        authConfig = mapper.convertValue(settingsMap, AuthConfig.class);
    }

    @Test
    void testRegisterEndpoint() throws Exception {
        String accessToken = getAccessToken();

        when(userRegistrationService.registerUser(accessToken)).thenReturn("User registered successfully");

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/register")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User registered successfully"));
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
}
