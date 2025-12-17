package com.tailan.confeitaria.web.services;

import com.tailan.confeitaria.web.domain.Category;
import com.tailan.confeitaria.web.services.dtos.category.CategoryDTO;
import com.tailan.confeitaria.web.services.dtos.category.CategoryResponseDTO;

public interface CategoryService {
    CategoryResponseDTO findByName(String name);
    void deleteCategoyByName(String name);
    CategoryResponseDTO  createCategory(CategoryDTO category);
    CategoryResponseDTO updateCategory(Long categoryId, CategoryDTO category);



     Category getCategory(String name);
}
