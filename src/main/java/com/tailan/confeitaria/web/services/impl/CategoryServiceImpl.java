package com.tailan.confeitaria.web.services.impl;

import com.tailan.confeitaria.web.domain.Category;
import com.tailan.confeitaria.web.domain.Product;
import com.tailan.confeitaria.web.infra.exception.ResourceNotFoundException;
import com.tailan.confeitaria.web.infra.exception.ResourceThisPresentException;
import com.tailan.confeitaria.web.repository.CategoryRepository;
import com.tailan.confeitaria.web.services.CategoryService;
import com.tailan.confeitaria.web.services.dtos.request.CategoryDTO;
import com.tailan.confeitaria.web.services.dtos.response.CategoryResponseDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Cacheable(value = "category-by-id")
    public CategoryResponseDTO findByName(String name) {
        Category category = getCategory(name);
        return new CategoryResponseDTO(category.getId(), category.getName(),  category.getProducts());

    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public void deleteCategoyByName(String name) {
        Category category = getCategory(name);
        if (!category.getProducts().isEmpty()){
            throw new IllegalArgumentException("This category has a product registered. It cannot be deleted.");
        }
        categoryRepository.delete(category);
    }

    @CacheEvict(value = "categories" ,allEntries = true)
    @Override
    public CategoryResponseDTO createCategory(CategoryDTO category) {
        Set<Product> products = new HashSet<>();

        //Se tiver uma categoria com esse nome, não vai ser possivel criar uma com o mesmo nome.
        Optional<Category> categoryEntity = categoryRepository.findByName(category.name());
        if (categoryEntity.isPresent()) {
            throw new ResourceThisPresentException("This category already exists");
        }

        Category newCategory = new Category();
        newCategory.setName(category.name());
        newCategory.setProducts(products);
        Category savedCategory = categoryRepository.save(newCategory);

        return new CategoryResponseDTO(savedCategory.getId(), savedCategory.getName(), savedCategory.getProducts());
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public CategoryResponseDTO updateCategory(Long categoryId, CategoryDTO category) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Category checkCategory = getCategory(category.name());

        if (!checkCategory.getId().equals(categoryId)) {
            throw new ResourceThisPresentException("Name already in use");

        }
        existingCategory.setName(category.name());
        Category savedCategory = categoryRepository.save(existingCategory);
        return new CategoryResponseDTO(savedCategory.getId(), savedCategory.getName(), savedCategory.getProducts());

    }

    @Cacheable(value = "categories")
    @Override
    public Page<CategoryResponseDTO> getAllCategories(int page, int size) {
        Pageable pageable1 = PageRequest.of(page, size);
        Page<Category> categories = categoryRepository.findAll(pageable1);

        Page<CategoryResponseDTO> responseDTOPage = categories.map(category -> new CategoryResponseDTO(category.getId(), category.getName(), category.getProducts()));
        return responseDTOPage;
    }

    @Override
    public Category getCategory(String name) {
        return categoryRepository.findByName(name).orElseThrow(() ->  new ResourceNotFoundException("Category not found!"));
    }
}
