package com.project.ecommerce.service;

import com.project.ecommerce.model.Category;
import com.project.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean createCategory(Category category) {
        Optional<Category> categoryToAdd = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
        if(categoryToAdd.isEmpty()){
            categoryRepository.save(category);
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public boolean deleteCategory(Long categoryId) {
        Optional<Category> categoryToDelete = categoryRepository.findById(categoryId);
        if(categoryToDelete.isEmpty()){
            return false;
        }
        categoryRepository.delete(categoryToDelete.get());
        return true;
    }

    @Override
    public boolean updateCategory(Long categoryId, Category category) {
        Optional<Category> categoryToUpdate = categoryRepository.findById(categoryId);
        if(categoryToUpdate.isPresent()){
            Category updatedCategory = categoryToUpdate.get();
            if(category.getCategoryName() != null){
                updatedCategory.setCategoryName(category.getCategoryName());
                categoryRepository.save(updatedCategory);
            }
            return true;
        }
        else{
            return false;
        }
    }
}
