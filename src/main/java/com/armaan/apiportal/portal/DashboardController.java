package com.armaan.apiportal.portal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    @GetMapping("/free-data")
    public ResponseEntity<String> getFreeData()
    {
        return ResponseEntity.ok("Welcome to the Free Tier Data Pool!");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_PRO')")
    @GetMapping("/premium-data")
    public ResponseEntity<String> getPremiumData()
    {
        return ResponseEntity.ok("Welcome to the Premium Developer Cluster. Confidential Pro Data Accessed.");
    }

}
