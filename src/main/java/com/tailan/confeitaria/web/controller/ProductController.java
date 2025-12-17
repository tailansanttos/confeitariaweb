package com.tailan.confeitaria.web.controller;

import com.tailan.confeitaria.web.services.ProductService;
import com.tailan.confeitaria.web.services.dtos.api.ApiResponseDTO;
import com.tailan.confeitaria.web.services.dtos.product.ProductRequestDTO;
import com.tailan.confeitaria.web.services.dtos.product.ProductResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        ProductResponseDTO responseDTO = productService.createProduct(productRequestDTO);
        ApiResponseDTO apiResponseDTO = new  ApiResponseDTO(responseDTO, HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO> findAllProducts(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                          @RequestParam(value = "size", defaultValue = "10", required = false)int size,
                                                          @RequestParam(value = "orderBy", defaultValue = "price", required = false) String sortBy,
                                                          @RequestParam(value = "direction", defaultValue = "ASC", required = false)String direction,
                                                          @RequestParam(value = "name", required = false) String name,
                                                          @RequestParam(value = "category", required = false) String category) {
        Page<ProductResponseDTO> productResponseDTOS = productService.findAll(page, size, sortBy, direction, name, category);
        ApiResponseDTO apiResponseDTO = new  ApiResponseDTO(productResponseDTOS, HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{product}")
    public ResponseEntity<ApiResponseDTO> findProduct(@PathVariable String product) {
        ProductResponseDTO productResponseDTO = productService.findByName(product);
        ApiResponseDTO apiResponseDTO = new  ApiResponseDTO(productResponseDTO, HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{product}")
    public ResponseEntity<Void> desactivateProduct(@PathVariable("product") String product) {
        productService.deactivateProductByName(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{product}")
    public ResponseEntity<Void> activateProduct(@PathVariable("product") String product) {
        productService.activateProductByName(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponseDTO> updateProduct(@PathVariable("productId") Long productId, @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.updateProduct(productId, productRequestDTO);
        ApiResponseDTO apiResponseDTO = new  ApiResponseDTO(productResponseDTO, HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }


}
