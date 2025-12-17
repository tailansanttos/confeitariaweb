package com.tailan.confeitaria.web.utils.mapper;

import com.tailan.confeitaria.web.domain.Category;
import com.tailan.confeitaria.web.domain.Product;
import com.tailan.confeitaria.web.services.dtos.product.ProductRequestDTO;
import com.tailan.confeitaria.web.services.dtos.product.ProductResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponseDTO toResponseDTO(Product product) {
        return new ProductResponseDTO(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImgUrl(), product.getActive(),product.getCategory().getName());
    }

    public Product toEntity(ProductRequestDTO requestDTO, Category category) {
        Product product = new Product();
        product.setName(requestDTO.name());
        product.setDescription(requestDTO.description());
       product.setPrice(requestDTO.price());
       product.setImgUrl(requestDTO.imgUrl());
       product.setActive(requestDTO.active());
       product.setQuantity(requestDTO.quantity());
       product.setCategory(category);
       return product;
    }
}
