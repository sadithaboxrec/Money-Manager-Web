package com.sadi.FinanceManager.service;

import com.sadi.FinanceManager.dto.ProfileDTO;
import com.sadi.FinanceManager.dto.ProfileResponse;

public interface ProfileService {

    ProfileResponse registerProfile(ProfileDTO profileDTO);

    boolean activateProfile(String activationToken);
}
