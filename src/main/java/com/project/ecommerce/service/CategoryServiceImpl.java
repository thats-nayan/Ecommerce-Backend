package com.project.ecommerce.service;

import com.project.ecommerce.dto.CategoryDTO;
import com.project.ecommerce.dto.CategoryResponse;
import com.project.ecommerce.exceptions.ResourceAlreadyExistsException;
import com.project.ecommerce.exceptions.ResourceNotFoundException;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<CategoryDTO> categories = categoryRepository.findAll().stream().map(
                category -> modelMapper.map(category, CategoryDTO.class)
        ).toList();
        return new CategoryResponse(categories);
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO category){
        Optional<Category> existingCategory = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
        if(existingCategory.isPresent()) {
            throw new ResourceAlreadyExistsException("Category", "categoryName", category.getCategoryName());
        }
        Category categoryToAdd = modelMapper.map(category, Category.class);
        categoryRepository.save(categoryToAdd);
        return modelMapper.map(categoryToAdd, CategoryDTO.class);
    }
    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Optional<Category> categoryToDelete = categoryRepository.findById(categoryId);
        if(categoryToDelete.isEmpty()){
            throw new ResourceNotFoundException("Category","categoryId",categoryId);
        }
        categoryRepository.delete(categoryToDelete.get());
        return modelMapper.map(categoryToDelete.get(), CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO category) {
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
        }
        categoryRepository.save(updatedCategory);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }
}
