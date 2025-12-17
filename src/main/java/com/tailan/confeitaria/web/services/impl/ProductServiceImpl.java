package com.tailan.confeitaria.web.services.impl;

import com.tailan.confeitaria.web.domain.Category;
import com.tailan.confeitaria.web.domain.Product;
import com.tailan.confeitaria.web.infra.exception.ResourceNotFoundException;
import com.tailan.confeitaria.web.infra.exception.ResourceThisPresentException;
import com.tailan.confeitaria.web.utils.mapper.ProductMapper;
import com.tailan.confeitaria.web.repository.ProductRepository;
import com.tailan.confeitaria.web.services.CategoryService;
import com.tailan.confeitaria.web.services.ProductService;
import com.tailan.confeitaria.web.services.dtos.product.ProductRequestDTO;
import com.tailan.confeitaria.web.services.dtos.product.ProductResponseDTO;
import com.tailan.confeitaria.web.utils.specifications.ProductSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponseDTO findByName(String name) {
        Product existingProduct = productRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return productMapper.toResponseDTO(existingProduct);
    }

    private Product getByName(String name){
        Product existingProduct = productRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return existingProduct;
    }

    @Override
    public void deactivateProductByName(String name) {
        Product existingProduct = getByName(name);
        existingProduct.setActive(false);
        productRepository.save(existingProduct);
    }

    @Override
    public void activateProductByName(String name) {
        Product existingProduct = getByName(name);
        existingProduct.setActive(true);
        productRepository.save(existingProduct);
    }

    @Override
    public Page<ProductResponseDTO> findAll(int page, int size, String sortBy,  String direction, String name, String category) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortBy);

        //Começa com os  produtos  ativos
        Specification<Product> specification   = Specification.where(ProductSpecifications.isActive());

        if (name != null  && !name.isEmpty()) {
            specification = specification.and(ProductSpecifications.productNameLikeIgnoreCase(name));
        }

        if (category != null  && !category.isEmpty()) {
            specification  = specification.and(ProductSpecifications.categoryNameEquals(category));
        }

        Page<Product> productPage = productRepository.findAll(specification, pageable);
        return productPage.map(productMapper::toResponseDTO);
    }


    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO product) {
        Category category = getCategoryByName(product.category());
       Optional<Product> existingProduct = productRepository.findByName(product.name());

       if (existingProduct.isPresent()) {
           throw new ResourceThisPresentException("Product already exists");
       }

       Product newProduct = productMapper.toEntity(product, category);
       return productMapper.toResponseDTO(productRepository.save(newProduct));

    }

    @Override
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO product) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        existingProduct.setName(product.name());
        if (!existingProduct.getCategory().getName().equals(product.category())) {
            Category newCategory = getCategoryByName(product.category());
            existingProduct.setCategory(newCategory);
        }
        existingProduct.setCategory(getCategoryByName(product.category()));
        existingProduct.setActive(product.active());
        existingProduct.setPrice(product.price());
        existingProduct.setQuantity(product.quantity());
        existingProduct.setDescription(product.description());
        existingProduct.setImgUrl(product.imgUrl());

        Product updateProduct  = productRepository.save(existingProduct);
        return productMapper.toResponseDTO((updateProduct));
    }

    private Category getCategoryByName(String name) {
        Category category = categoryService.getCategory(name);
        return  category;
    }
}
