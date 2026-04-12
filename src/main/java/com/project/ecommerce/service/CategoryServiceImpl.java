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
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
    }
    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categories.stream().filter(c -> c.getCategoryId().equals(categoryId)).findFirst().orElse(null);
        if(category != null){
            categories.remove(category);
            return "Category deleted successfully";
        }
        else{
            return "Category not found";
        }
    }
}
