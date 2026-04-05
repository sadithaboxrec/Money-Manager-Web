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
    public ResponseEntity<String> activateProfile(@RequestParam String token) {

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


    @GetMapping("/test")
    public String test() {
        return "test Successful";
    }


}
