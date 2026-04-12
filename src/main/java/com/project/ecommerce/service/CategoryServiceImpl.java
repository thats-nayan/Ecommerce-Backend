package com.project.ecommerce.service;

import com.project.ecommerce.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public boolean createCategory(Category category) {
        Category categoryToAdd = categories.stream().filter(c -> c.getCategoryName().equalsIgnoreCase(category.getCategoryName())).findFirst().orElse(null);
        if(categoryToAdd == null){
            category.setCategoryId(nextId++);
            return categories.add(category);
        }
        else{
            return false;
        }
    }
    @Override
    public boolean deleteCategory(Long categoryId) {
        Category category = categories.stream().filter(c -> c.getCategoryId().equals(categoryId)).findFirst().orElse(null);
        return categories.remove(category);
    }
}
