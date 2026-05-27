package com.project.ecommerce.controller;

import com.project.ecommerce.config.AppConstants;
import com.project.ecommerce.dto.CategoryDTO;
import com.project.ecommerce.dto.CategoryResponse;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.CATEGORY_SORT_BY) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.SORT_DIR) String sortDir
    ) {
        return new ResponseEntity<>(categoryService.getAllCategories(pageNumber,pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/public/category/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
    }

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO category){
        return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        return new ResponseEntity<>(categoryService.deleteCategory(categoryId), HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId,@Valid @RequestBody CategoryDTO category) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryId,category), HttpStatus.OK);
    }
}
