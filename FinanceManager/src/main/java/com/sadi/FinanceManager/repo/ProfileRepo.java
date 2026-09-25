package com.sadi.FinanceManager.repo;

import com.sadi.FinanceManager.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepo extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByEmail(String email);

    Optional<Profile> findByActivationToken(String activationToken);

    Optional<Profile> findFirstByEmail(String email);


    Optional<Profile> findByResetPasswordToken(String resetPasswordToken);
}
