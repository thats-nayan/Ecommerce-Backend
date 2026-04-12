package com.project.ecommerce.controller;

import com.project.ecommerce.model.Category;
import com.project.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PostMapping("/api/admin/category")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        boolean status = categoryService.createCategory(category);
        if(status){
            return new ResponseEntity<>("Category created successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Category already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        boolean status = categoryService.deleteCategory(categoryId);
        if (status) {
            return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
        }
    }
}
