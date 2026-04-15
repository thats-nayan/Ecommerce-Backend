package com.project.ecommerce.controller;

import com.project.ecommerce.model.Category;
import com.project.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PostMapping("/admin/category")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        boolean status = categoryService.createCategory(category);
        if(status){
            return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("Category already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        boolean status = categoryService.deleteCategory(categoryId);
        if (status) {
            return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        boolean status = categoryService.updateCategory(categoryId, category);
        if (status) {
            return new ResponseEntity<>("Category updated successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Category cannot be updated", HttpStatus.NOT_FOUND);
        }
    }
}
