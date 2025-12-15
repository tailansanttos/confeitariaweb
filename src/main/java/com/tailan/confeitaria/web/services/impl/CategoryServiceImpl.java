package com.tailan.confeitaria.web.services.impl;

import com.tailan.confeitaria.web.domain.Category;
import com.tailan.confeitaria.web.domain.Product;
import com.tailan.confeitaria.web.infra.exception.ResourceNotFoundException;
import com.tailan.confeitaria.web.infra.exception.ResourceThisPresentException;
import com.tailan.confeitaria.web.repository.CategoryRepository;
import com.tailan.confeitaria.web.services.CategoryService;
import com.tailan.confeitaria.web.services.dtos.category.CategoryDTO;
import com.tailan.confeitaria.web.services.dtos.category.CategoryResponseDTO;
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
    public CategoryResponseDTO findByName(String name) {
        Category category = categoryRepository.findByName(name).orElseThrow(() ->  new ResourceNotFoundException(name));
        CategoryResponseDTO response = new CategoryResponseDTO(category.getId(), category.getName(),  category.getProducts());
        return  response;
    }

    @Override
    public void deleteCategoyByName(String name) {
        Category category = categoryRepository.findByName(name).orElseThrow(() ->  new ResourceNotFoundException(name));
        if (category.getProducts().size()  >  0){
            throw new IllegalArgumentException("This category has a product registered. It cannot be deleted.");
        }
        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryDTO category) {
        Set<Product> products = new HashSet<>();

        //Se tiver uma categoria com esse nome, não vai ser possivel criar uma com o mesmo nome.
        Optional<Category> categoryEntity = categoryRepository.findByName(category.name());
        if (categoryEntity.isPresent()) {
            throw new ResourceThisPresentException(category.name());
        }

        Category newCategory = new Category();
        newCategory.setName(category.name());
        newCategory.setProducts(products);
        Category savedCategory = categoryRepository.save(newCategory);

        return new CategoryResponseDTO(savedCategory.getId(), savedCategory.getName(), savedCategory.getProducts());
    }

    @Override
    public CategoryResponseDTO updateCategory(CategoryDTO category) {
        CategoryResponseDTO existingCategory = findByName(category.name());
        return existingCategory;
    }
}
