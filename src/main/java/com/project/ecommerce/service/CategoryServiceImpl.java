package com.project.ecommerce.service;

import com.project.ecommerce.exceptions.ResourceAlreadyExistsException;
import com.project.ecommerce.exceptions.ResourceNotFoundException;
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
    public void createCategory(Category category){
        Optional<Category> categoryToAdd = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
        if(categoryToAdd.isPresent()){
            throw new ResourceAlreadyExistsException("Category","categoryName",category.getCategoryName());
        }
        categoryRepository.save(category);
    }
    @Override
    public void deleteCategory(Long categoryId) {
        Optional<Category> categoryToDelete = categoryRepository.findById(categoryId);
        if(categoryToDelete.isEmpty()){
            throw new ResourceNotFoundException("Category","categoryId",categoryId);
        }
        categoryRepository.delete(categoryToDelete.get());
    }

    @Override
    public void updateCategory(Long categoryId, Category category) {
        Optional<Category> categoryToUpdate = categoryRepository.findById(categoryId);
        if(categoryToUpdate.isEmpty()){
            throw new ResourceNotFoundException("Category","categoryId",categoryId);
        }
        Optional<Category> existingCategory = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
        if(existingCategory.isPresent()){
            throw new ResourceAlreadyExistsException("Category","categoryName",category.getCategoryName());
        }
        Category updatedCategory = categoryToUpdate.get();
        if(category.getCategoryName() != null){
            updatedCategory.setCategoryName(category.getCategoryName());
            categoryRepository.save(updatedCategory);
        }
    }
}
