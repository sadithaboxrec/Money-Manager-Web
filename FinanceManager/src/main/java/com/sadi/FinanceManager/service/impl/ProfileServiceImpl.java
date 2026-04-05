package com.sadi.FinanceManager.service.impl;

import com.sadi.FinanceManager.dto.ProfileDTO;
import com.sadi.FinanceManager.dto.ProfileResponse;
import com.sadi.FinanceManager.entity.Profile;
import com.sadi.FinanceManager.repo.ProfileRepo;
import com.sadi.FinanceManager.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepo profileRepo;

    private final EmailServiceImpl emailServiceImpl;

    @Override
    public ProfileResponse registerProfile(ProfileDTO profileDTO) {

        Profile profile = new Profile();

        profile.setId(profileDTO.getId());
        profile.setFullName(profileDTO.getFullName());
        profile.setEmail(profileDTO.getEmail());
        profile.setPassword(profileDTO.getPassword());
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



    public boolean activateProfile(String activationToken) {
        return profileRepo.findByActivationToken(activationToken)
                .map(profile -> {
                    profile.setIsActive(true);
                    profileRepo.save(profile);
                    return true;
                })
                .orElse(false);
    }

}
