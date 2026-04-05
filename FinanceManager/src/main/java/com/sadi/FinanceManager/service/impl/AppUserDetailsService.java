package com.sadi.FinanceManager.service.impl;

import com.sadi.FinanceManager.entity.Profile;
import com.sadi.FinanceManager.repo.ProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final ProfileRepo profileRepo;

    // responsible for loading the profile fom database

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Profile existingProfile = profileRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Profile not found with: " + email));

        //  convert to spring security user

        return User.builder()
                .username(existingProfile.getEmail())
                .password(existingProfile.getPassword())
                .authorities(Collections.emptyList())
                .build();


    }
}
