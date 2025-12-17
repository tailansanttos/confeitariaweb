package com.tailan.confeitaria.web.services;

import com.tailan.confeitaria.web.services.dtos.category.CategoryResponseDTO;
import com.tailan.confeitaria.web.services.dtos.product.ProductRequestDTO;
import com.tailan.confeitaria.web.services.dtos.product.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponseDTO findByName(String name);

    void deactivateProductByName(String name);
    void activateProductByName(String name);

    Page<ProductResponseDTO> findAll(int page, int size, String sortBy, String direction, String name, String category);

    ProductResponseDTO createProduct(ProductRequestDTO product);
    ProductResponseDTO updateProduct(Long productId, ProductRequestDTO product);


}
