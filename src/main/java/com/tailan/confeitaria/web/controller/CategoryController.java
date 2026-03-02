package com.tailan.confeitaria.web.controller;

import com.tailan.confeitaria.web.services.CategoryService;
import com.tailan.confeitaria.web.services.dtos.response.ApiResponseDTO;
import com.tailan.confeitaria.web.services.dtos.request.CategoryDTO;
import com.tailan.confeitaria.web.services.dtos.response.CategoryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{category}")
    @Operation(description = "Deve retornar uma categoria pelo nome")
    @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso.")
    public  ResponseEntity<ApiResponseDTO> getCategory(@PathVariable("categoryId") String category){
        CategoryResponseDTO response = categoryService.findByName(category);
        ApiResponseDTO responseDTO =  new ApiResponseDTO(response, HttpStatus.OK.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @Operation(description = "Deve retornar as categorias paginados")
    @ApiResponse(responseCode = "200", description = "Categorias retornadas com sucesso.")
    @GetMapping
    public ResponseEntity<ApiResponseDTO> findAllCategories(@RequestParam("page")  int page, @RequestParam("size") int size){
        Page<CategoryResponseDTO> categories = categoryService.getAllCategories(page, size);
        ApiResponseDTO responseDTO =  new ApiResponseDTO(categories, HttpStatus.OK.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDTO>updateCategory(@PathVariable("categoryId")Long categoryId,@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryResponseDTO categoryResponseDTO =  categoryService.updateCategory(categoryId, categoryDTO);
        ApiResponseDTO responseDTO =  new ApiResponseDTO(categoryResponseDTO, HttpStatus.OK.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{category}")
    public ResponseEntity<ApiResponseDTO> deleteCategory(@PathVariable("category") String category){
        categoryService.deleteCategoyByName(category);
        ApiResponseDTO responseDTO =  new ApiResponseDTO(HttpStatus.NO_CONTENT.value(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.NO_CONTENT);
    }
}
