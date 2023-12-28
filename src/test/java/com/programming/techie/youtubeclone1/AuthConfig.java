package com.programming.techie.youtubeclone1;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class AuthConfig {

    String authUrl;
    String clientId;
    String clientSecret;
    String audience;
    String grantType;
}
