package com.armaan.apiportal.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitingService {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(UserDetails userDetails) {
        return cache.computeIfAbsent(userDetails.getUsername(), email -> createNewBucket(userDetails));
    }

    private Bucket createNewBucket(UserDetails userDetails) {
        // Safe check: look through all authorities to see if ANY contain "PRO"
        boolean isPro = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_PRO") || auth.equals("PRO"));

        if (isPro) {
            // PRO Tier: Up to 100 requests per minute
            return Bucket.builder()
                    .addLimit(Bandwidth.classic(100, Refill.intervally(100, Duration.ofMinutes(1))))
                    .build();
        }

        // FREE Tier: Up to 10 requests per minute
        return Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1))))
                .build();
    }

    // Helper method to clear cache if a user upgrades roles while the server is running
    public void deleteBucket(String email) {
        cache.remove(email);
    }
}