package com.programming.techie.youtubeclone1.controllers;

import com.programming.techie.youtubeclone1.exception.ErrorDTO;
import com.programming.techie.youtubeclone1.services.UserRegistrationService;
import com.programming.techie.youtubeclone1.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static java.lang.System.lineSeparator;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
        @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))})
@RequestMapping("/api/users")
@Tag(name = "User")
public class UserController {

    private final UserRegistrationService userRegistrationService;
    private final UserService userService;

    @GetMapping("/register")
    @ResponseStatus(OK)
    @Operation(summary = "API for registering user")
    @ApiResponse(responseCode = "200", description = "User has been registered")
    public String register(Authentication authentication) {
        log.info("Received HTTP request for GET /api/users/register");
        Jwt jwt = (Jwt) authentication.getPrincipal();
        final String registrationResult = userRegistrationService.registerUser(jwt.getTokenValue());
        log.info("Produced HTTP 200 response for GET /api/users/register:{}", registrationResult);
        return registrationResult;
    }

    @PostMapping("/subscribe/{userId}")
    @ResponseStatus(OK)
    @Operation(summary = "API for subscribing a user")
    @ApiResponse(responseCode = "200", description = "User has been subscribed")
    public boolean subscribeToUser(
            @Parameter(name = "UserId", example = "658b3462b7988017733761b9")
            @PathVariable
            String userId) {
        log.info("Received HTTP request POST /api/users/subscribe/{}", userId);
        userService.subscribeToUser(userId);
        log.info("Produced HTTP 200 response for POST /api/users/subscribe/{}", userId);
        return true;
    }

    @PostMapping("/unsubscribe/{userId}")
    @ResponseStatus(OK)
    @Operation(summary = "API for unsubscribing a user")
    @ApiResponse(responseCode = "200", description = "User has been unsubscribed")
    public boolean unsubscribeToUser(
            @Parameter(name = "UserId", example = "658b3462b7988017733761b9")
            @PathVariable
            String userId) {
        log.info("Received HTTP request POST /api/users/unsubscribe/{}", userId);
        userService.unsubscribeToUser(userId);
        log.info("Produced HTTP 200 response for POST /api/users/unsubscribe/{}", userId);
        return true;
    }

    @GetMapping("/{userId}/history")
    @ResponseStatus(OK)
    @Operation(summary = "API for retrieving the user's video history")
    @ApiResponse(responseCode = "200", description = "The user's video history has been retrieved")
    public Set<String> getUserVideoHistory(
            @Parameter(name = "UserId", example = "658b3462b7988017733761b9")
            @PathVariable
            String userId) {
        log.info("Received HTTP request GET /api/users/{}/history", userId);
        final Set<String> response = userService.getUserVideoHistory(userId);
        log.info("Produced HTTP 200 response for GET /api/users/{}/history:{}{}", userId, lineSeparator(), response);
        return response;
    }
}
