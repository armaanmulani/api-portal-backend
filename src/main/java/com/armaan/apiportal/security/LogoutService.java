package com.armaan.apiportal.security;

import com.armaan.apiportal.model.BlacklistedToken;
import com.armaan.apiportal.repository.BlacklistedTokenRepository;
import com.armaan.apiportal.repository.RefreshTokenRepository;
import com.armaan.apiportal.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService; // Your existing JWT utility service

    @Transactional
    public void logout(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header format");
        }

        String jwt = authHeader.substring(7);
        String email = jwtService.extractUsername(jwt);

        // 1. Extract natural expiration date from JWT to set our database TTL
        Date expiration = jwtService.extractClaim(jwt, Claims::getExpiration);

        // 2. Add the Access Token to the Blacklist
        BlacklistedToken blacklistedToken = BlacklistedToken.builder()
                .token(jwt)
                .expiryDate(expiration.toInstant())
                .build();
        blacklistedTokenRepository.save(blacklistedToken);

        // 3. Delete the user's Refresh Token so they can't sneakily request a new Access Token
        userRepository.findByEmail(email).ifPresent(refreshTokenRepository::deleteByUser);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.existsByToken(token);
    }
}