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
}
