package com.sadi.FinanceManager.service;

import com.sadi.FinanceManager.dto.AuthRequest;
import com.sadi.FinanceManager.dto.ProfileDTO;
import com.sadi.FinanceManager.dto.ProfileResponse;
import com.sadi.FinanceManager.entity.Profile;

import java.util.Map;

public interface ProfileService {

    ProfileResponse registerProfile(ProfileDTO profileDTO);

    boolean activateProfile(String activationToken);

    boolean isAccountActive(String email);

    Profile getCurrentProfile();

    ProfileResponse getPublicProfile(String email);

    Map<String, Object> authenticateAndGenerateToken(AuthRequest authRequest);

    boolean hasCustomerWithEmail(String email);
}
