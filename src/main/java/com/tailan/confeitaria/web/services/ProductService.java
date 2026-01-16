package com.tailan.confeitaria.web.services;

import com.tailan.confeitaria.web.domain.Product;
import com.tailan.confeitaria.web.services.dtos.request.ProductRequestDTO;
import com.tailan.confeitaria.web.services.dtos.response.ProductResponseDTO;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductResponseDTO findByName(String name);

    void deactivateProductByName(String name);
    void activateProductByName(String name);

    Page<ProductResponseDTO> findAll(int page, int size, String sortBy, String direction, String name, String category);

    ProductResponseDTO createProduct(ProductRequestDTO product);
    ProductResponseDTO updateProduct(Long productId, ProductRequestDTO product);

    Product getProductById(Long productId);

    Boolean productDisponible(Product product, Integer quantity);

    void reduceStock(Long productId, Integer quantity);
    void updateStock(Long productId, Integer quantity);


}
