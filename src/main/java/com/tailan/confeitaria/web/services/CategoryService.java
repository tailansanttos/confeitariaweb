package com.tailan.confeitaria.web.services;

import com.tailan.confeitaria.web.domain.Category;
import com.tailan.confeitaria.web.services.dtos.request.CategoryDTO;
import com.tailan.confeitaria.web.services.dtos.response.CategoryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryResponseDTO findByName(String name);

    void deleteCategoyByName(String name);

    CategoryResponseDTO  createCategory(CategoryDTO category);
    CategoryResponseDTO updateCategory(Long categoryId, CategoryDTO category);

    Page<CategoryResponseDTO> getAllCategories(int page, int size);

     Category getCategory(String name);
}
