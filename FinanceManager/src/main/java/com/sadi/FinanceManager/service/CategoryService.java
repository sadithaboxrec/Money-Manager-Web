package com.sadi.FinanceManager.service;

import com.sadi.FinanceManager.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    List<CategoryDTO> getCategoriesForCurrentUser();

    List<CategoryDTO> getCategoriesByTypeForCurrentUser(String categoryType);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO dto);

}
