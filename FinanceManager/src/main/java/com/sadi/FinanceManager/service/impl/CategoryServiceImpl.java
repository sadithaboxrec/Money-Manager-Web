package com.sadi.FinanceManager.service.impl;

import com.sadi.FinanceManager.dto.CategoryDTO;
import com.sadi.FinanceManager.entity.Category;
import com.sadi.FinanceManager.entity.Profile;
import com.sadi.FinanceManager.exception.DuplicateResourceException;
import com.sadi.FinanceManager.repo.CategoryRepo;
import com.sadi.FinanceManager.service.CategoryService;
import com.sadi.FinanceManager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final ProfileService profileService;


    //save category
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {

        Profile profile = profileService.getCurrentProfile();

        if (categoryRepo.existsByNameAndProfileId(categoryDTO.getName(), profile.getId())) {
//            throw new RuntimeException("Category with this name already there");
            throw new DuplicateResourceException("Category already exists");
        }

        Category newCategory = new Category() ;
        newCategory.setName(categoryDTO.getName());
        newCategory.setProfile(profile);
        newCategory.setIcon(categoryDTO.getIcon());
        newCategory.setCategoryType(categoryDTO.getCategoryType());

        Category savedCategory= categoryRepo.save(newCategory);

        return CategoryDTO.builder()
                .id(savedCategory.getId())
                .profileId(savedCategory.getProfile() != null ?  savedCategory.getProfile().getId(): null)
                .name(savedCategory.getName())
                .icon(savedCategory.getIcon())
                .createdAt(savedCategory.getCreatedAt())
                .updatedAt(savedCategory.getUpdatedAt())
                .categoryType(savedCategory.getCategoryType())
                .build();
    }


    //get categories of current user
    public List<CategoryDTO> getCategoriesForCurrentUser() {

        Profile profile = profileService.getCurrentProfile();

        List<Category> categories = categoryRepo.findByProfileId(profile.getId());
        List<CategoryDTO> categoriesDTO = new ArrayList<>();

        for (Category category : categories) {

            categoriesDTO.add(
                    new CategoryDTO(
                            category.getId(),
                            category.getProfile().getId(),
                            category.getName(),
                            category.getIcon(),
                            category.getCategoryType(),
                            category.getCreatedAt(),
                            category.getUpdatedAt()
                    )
            );
        }

        return categoriesDTO;
    }



   ///   get categories by category type for the user
    public List<CategoryDTO> getCategoriesByTypeForCurrentUser(String categoryType) {
        Profile profile = profileService.getCurrentProfile();

        List<Category> categories = categoryRepo.findByCategoryTypeAndProfileId(categoryType, profile.getId());

        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for (Category category : categories) {

            categoryDTOList.add(
                    new CategoryDTO(
                            category.getId(),
                            category.getProfile().getId(),
                            category.getName(),
                            category.getIcon(),
                            category.getCategoryType(),
                            category.getCreatedAt(),
                            category.getUpdatedAt()
                    )
            );
        }

        return categoryDTOList;
    }



    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {

        Profile profile = profileService.getCurrentProfile();

        Category existingCategory = categoryRepo.findByIdAndProfileId(categoryId, profile.getId())
                .orElseThrow(() -> new RuntimeException("Category not found or no access"));

        existingCategory.setName(categoryDTO.getName());
        existingCategory.setIcon(categoryDTO.getIcon());
        existingCategory.setCategoryType(categoryDTO.getCategoryType());

        Category updatedCategory = categoryRepo.save(existingCategory);

        return  CategoryDTO.builder()
                .id(updatedCategory.getId())
                .name(updatedCategory.getName())
                .icon(updatedCategory.getIcon())
                .categoryType(updatedCategory.getCategoryType())
                .updatedAt(updatedCategory.getUpdatedAt())
                .build();


    }

}
