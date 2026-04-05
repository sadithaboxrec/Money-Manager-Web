package com.sadi.FinanceManager.repo;

import com.sadi.FinanceManager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    // find all categories by profile
    List<Category> findByProfileId(UUID profileId);

    // find by id and profile
    Optional<Category> findByIdAndProfileId(Long id, UUID profileId);

    // find by type and profile
    List<Category> findByCategoryTypeAndProfileId(String categoryType, UUID profileId);

    // check if name exists for profile
    Boolean existsByNameAndProfileId(String name, UUID profileId);
}
