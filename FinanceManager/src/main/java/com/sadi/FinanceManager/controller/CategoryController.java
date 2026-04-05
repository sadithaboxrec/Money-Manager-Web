package com.sadi.FinanceManager.controller;

import com.sadi.FinanceManager.dto.CategoryDTO;
import com.sadi.FinanceManager.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDTO) {

        CategoryDTO savedCategory = categoryService.saveCategory(categoryDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }


    // categories of current users
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {

        List<CategoryDTO> categories = categoryService.getCategoriesForCurrentUser();

        return ResponseEntity.ok(categories);
    }


    @GetMapping("/{type}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByTypeForCurrentUser(@PathVariable String type) {

        List<CategoryDTO> list = categoryService.getCategoriesByTypeForCurrentUser(type);

        return ResponseEntity.ok(list);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId,
                                                      @RequestBody CategoryDTO categoryDTO) {

        CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);

        return ResponseEntity.ok(updatedCategory);
    }


}
