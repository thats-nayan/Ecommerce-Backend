package com.project.ecommerce.service;

import com.project.ecommerce.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    boolean createCategory(Category category);
    boolean deleteCategory(Long categoryId);

    boolean updateCategory(Long categoryId, Category category);
}
