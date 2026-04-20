package com.project.ecommerce.service;

import com.project.ecommerce.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);
    void deleteCategory(Long categoryId);
    void updateCategory(Long categoryId, Category category);
}
