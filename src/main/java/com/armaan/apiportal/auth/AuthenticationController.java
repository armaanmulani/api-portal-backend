package com.armaan.apiportal.auth;

import com.armaan.apiportal.model.RefreshToken;
import com.armaan.apiportal.model.TokenRefreshRequest;
import com.armaan.apiportal.security.JwtService;
import com.armaan.apiportal.security.LogoutService;
import com.armaan.apiportal.security.RefreshTokenService;
import com.armaan.apiportal.security.TokenRefreshException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final AuthenticationService service;
    private final LogoutService logoutService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);
                    return ResponseEntity.ok(AuthenticationResponse.builder()
                            .token(accessToken)
                            .refreshToken(request.getRefreshToken())
                            .build());
                })
                .orElseThrow(() -> new TokenRefreshException(request.getRefreshToken(), "Refresh token is not registered in the database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        logoutService.logout(authHeader);
        return ResponseEntity.ok("Logged out successfully. Tokens invalidated.");
    }
}