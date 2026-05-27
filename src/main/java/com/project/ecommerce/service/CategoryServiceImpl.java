package com.project.ecommerce.service;

import com.project.ecommerce.dto.CategoryDTO;
import com.project.ecommerce.dto.CategoryResponse;
import com.project.ecommerce.exceptions.ResourceAlreadyExistsException;
import com.project.ecommerce.exceptions.ResourceNotFoundException;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable("allCategories")
    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sortByAndOrder = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page <Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categories = categoryPage.getContent();

        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOs);

        // Set pagination metadata
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setPageNumber(pageDetails.getPageNumber());
        categoryResponse.setPageSize(pageDetails.getPageSize());
        categoryResponse.setLastPage(categoryPage.isLast());

        return categoryResponse;
    }

    @CacheEvict(value = "allCategories", allEntries = true)
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
    @CacheEvict(value = "categories", key = "#categoryId")
    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Optional<Category> categoryToDelete = categoryRepository.findById(categoryId);
        if(categoryToDelete.isEmpty()){
            throw new ResourceNotFoundException("Category","categoryId",categoryId);
        }
        categoryRepository.delete(categoryToDelete.get());
        return modelMapper.map(categoryToDelete.get(), CategoryDTO.class);
    }

    @CacheEvict(value = "categories", key = "#categoryId")
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

    @Cacheable(value = "categories" , key = "#categoryId")
    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isEmpty()){
            throw new ResourceNotFoundException("Category","categoryId",categoryId);
        }
        return modelMapper.map(category.get(), CategoryDTO.class);
    }
}
