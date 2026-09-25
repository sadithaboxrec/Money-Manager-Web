package com.sadi.FinanceManager.controller;

import com.sadi.FinanceManager.dto.AuthRequest;
import com.sadi.FinanceManager.dto.ProfileDTO;
import com.sadi.FinanceManager.dto.ProfileResponse;
import com.sadi.FinanceManager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<?> registerProfile(
           @RequestBody ProfileDTO profileDTO) {

        if(profileService.hasCustomerWithEmail(profileDTO.getEmail())){
            return new ResponseEntity<>("Customer Already exists with that email" , HttpStatus.NOT_ACCEPTABLE);
        }


        ProfileResponse response = profileService.registerProfile(profileDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


    @GetMapping("/activateprofile")
//    public ResponseEntity<String> activateProfile(@RequestParam String token) {
    public ResponseEntity<String> activateProfile(@RequestParam("token") String token) {
        boolean isActivated = profileService.activateProfile(token);
        if (isActivated) {
            return ResponseEntity.ok("Profile activated ");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activation token not found");
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest authRequest) {

        try {

            if (!profileService.isAccountActive(authRequest.getEmail())) {
                // user is not activated
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                        "message", "Account is not active. Please activate your account first."
                ));

            }
            Map<String, Object> response = profileService.authenticateAndGenerateToken(authRequest);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()
            ));
        }

    }


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) {

        try {
            profileService.forgotPassword(email);

            return ResponseEntity.ok("Password reset link has been sent to your email.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam("token") String token,
            @RequestParam("newPassword") String newPassword) {

        boolean isReset = profileService.resetPassword(token, newPassword);

        if (isReset) {
            return ResponseEntity.ok("Password reset successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid or expired reset token.");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getPublicvProfile() {
        ProfileResponse profileResponse= profileService.getPublicProfile(null);
        return ResponseEntity.ok(profileResponse);

        // to restore page after reload
    }


    @GetMapping("/test")
    public String test() {
        return "test Successful";
    }


}
