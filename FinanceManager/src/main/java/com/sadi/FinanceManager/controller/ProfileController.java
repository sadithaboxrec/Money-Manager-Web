package com.sadi.FinanceManager.controller;

import com.sadi.FinanceManager.dto.ProfileDTO;
import com.sadi.FinanceManager.dto.ProfileResponse;
import com.sadi.FinanceManager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<?> registerProfile(
           @RequestBody ProfileDTO profileDTO) {

        ProfileResponse response = profileService.registerProfile(profileDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


    @GetMapping("/activateprofile")
    public ResponseEntity<String> activateProfile(@RequestParam String token) {

        boolean isActivated = profileService.activateProfile(token);
        if (isActivated) {
            return ResponseEntity.ok("Profile activated ");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activation token not found");
        }
    }



}
