package com.project.ecommerce.repository;

import com.project.ecommerce.model.Category;
// Gives all crud operations
import org.springframework.data.repository.CrudRepository;
// Gives crud methods + extra methods - recommended
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryNameIgnoreCase(String categoryName);
}
