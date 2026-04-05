package com.sadi.FinanceManager.service.impl;

import com.sadi.FinanceManager.dto.AuthRequest;
import com.sadi.FinanceManager.dto.ProfileDTO;
import com.sadi.FinanceManager.dto.ProfileResponse;
import com.sadi.FinanceManager.entity.Profile;
import com.sadi.FinanceManager.repo.ProfileRepo;
import com.sadi.FinanceManager.service.ProfileService;
import com.sadi.FinanceManager.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepo profileRepo;

    private final EmailServiceImpl emailServiceImpl;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;


    @Override
    public ProfileResponse registerProfile(ProfileDTO profileDTO) {

        Profile profile = new Profile();

        profile.setId(profileDTO.getId());
        profile.setFullName(profileDTO.getFullName());
        profile.setEmail(profileDTO.getEmail());
//        profile.setPassword(profileDTO.getPassword());
        profile.setPassword(passwordEncoder.encode(profileDTO.getPassword()));
        profile.setProfileImageUrl(profileDTO.getProfileImageUrl());
        profile.setCreatedAt(profileDTO.getCreatedAt());
        profile.setUpdatedAt(profileDTO.getUpdatedAt());
        profile.setActivationToken(UUID.randomUUID().toString());


        // Set the activation email for token
        String activationLink="http://localhost:8080/api/v1.0/activateprofile?token="+profile.getActivationToken();
        String subject = "Activating Financial Manager account";
        String body = "Click on the  link to activate your account: " + activationLink;
        emailServiceImpl.sendEmail(profile.getEmail(), subject, body);

        Profile savedProfile = profileRepo.save(profile);

        return new ProfileResponse(
                savedProfile.getId(),
                savedProfile.getFullName(),
                savedProfile.getEmail(),
                savedProfile.getProfileImageUrl(),
                savedProfile.getCreatedAt(),
                savedProfile.getUpdatedAt()
        );
    }


    public boolean hasCustomerWithEmail(String email) {

        return profileRepo.findFirstByEmail(email).isPresent();
    }


    public boolean activateProfile(String activationToken) {
        return profileRepo.findByActivationToken(activationToken)
                .map(profile -> {
                    profile.setIsActive(true);
                    profileRepo.save(profile);
                    return true;
                })
                .orElse(false);
    }


    public boolean isAccountActive(String email) {

        return profileRepo.findByEmail(email)
                .map(Profile::getIsActive)
                .orElse(false);
    }

    public Profile getCurrentProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return profileRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Profile not found with email: " + authentication.getName()));
    }

    public ProfileResponse getPublicProfile(String email) {

        Profile currentUser = null;

        if (email == null) {
            currentUser = getCurrentProfile();
        }else {
            currentUser = profileRepo.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Profile not found with email: " + email));
        }

//        return new ProfileResponse(
//                currentUser.getId(),
//                currentUser.getFullName(),
//                currentUser.getEmail(),
//                currentUser.getProfileImageUrl(),
//                currentUser.getCreatedAt(),
//                currentUser.getUpdatedAt()
//                );

        return ProfileResponse.builder()
                .id(currentUser.getId())
                .fullName(currentUser.getFullName())
                .email(currentUser.getEmail())
                .profileImageUrl(currentUser.getProfileImageUrl())
                .createdAt(currentUser.getCreatedAt())
                .updatedAt(currentUser.getUpdatedAt())
                .build();
    }




    public Map<String, Object> authenticateAndGenerateToken(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(),
                    authRequest.getPassword()));

            // jwt token
           String token= jwtUtil.generateToken(authRequest.getEmail());

            return Map.of(
                    "token", token,
                    "user", getPublicProfile(authRequest.getEmail())
            );
        } catch (Exception e) {
            throw new RuntimeException("Wrong email or password");
        }


    }

}
