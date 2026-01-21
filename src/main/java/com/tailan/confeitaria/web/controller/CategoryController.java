package com.tailan.confeitaria.web.controller;

import com.tailan.confeitaria.web.services.CategoryService;
import com.tailan.confeitaria.web.services.dtos.response.ApiResponseDTO;
import com.tailan.confeitaria.web.services.dtos.request.CategoryDTO;
import com.tailan.confeitaria.web.services.dtos.response.CategoryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Category")

public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @Operation(description = "Deve criar uma nova categoria, apenas ADMINS.")
    public ResponseEntity<ApiResponseDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryResponseDTO categoryResponseDTO =  categoryService.createCategory(categoryDTO);
        ApiResponseDTO responseDTO =  new ApiResponseDTO(categoryResponseDTO, HttpStatus.CREATED.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
