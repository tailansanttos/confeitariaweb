package com.tailan.confeitaria.web.services.impl;

import com.tailan.confeitaria.web.domain.Category;
import com.tailan.confeitaria.web.domain.Product;
import com.tailan.confeitaria.web.infra.exception.ResourceThisPresentException;
import com.tailan.confeitaria.web.repository.ProductRepository;
import com.tailan.confeitaria.web.services.dtos.category.CategoryDTO;
import com.tailan.confeitaria.web.services.dtos.product.ProductRequestDTO;
import com.tailan.confeitaria.web.services.dtos.product.ProductResponseDTO;
import com.tailan.confeitaria.web.utils.mapper.ProductMapper;
import com.tailan.confeitaria.web.utils.specifications.ProductSpecifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private CategoryServiceImpl categoryService;

    @Mock
    private ProductMapper productMapper;
    @Mock
    private ProductRepository productRepository;

    Product product;
    Category category;
    Set<Product> products;
    ProductRequestDTO productRequest;
    CategoryDTO categoryRequest;

    @BeforeEach
    void setUp() {
        products = new HashSet<>();

        categoryRequest = new CategoryDTO("BOLOS");
        category = new Category(1L, categoryRequest.name(), products);

        productRequest = new ProductRequestDTO
                ("Bolo de pote sabor chocolate","Bolo 250g sabor chocolate",new BigDecimal(8),"bolochocolate.png", true, "BOLOS", 5);
        product = new Product(1L, productRequest.name(), productRequest.description(),productRequest.price(),productRequest.quantity(),productRequest.imgUrl(),productRequest.active(),category);
    }

    @Test
    @DisplayName("Teste deve cadastrar um produto com sucesso.")
    public void youMustSuccessfullyRegisterAProduct(){
        when(categoryService.getCategory(categoryRequest.name())).thenReturn(category);
        when(productRepository.findByName(productRequest.name())).thenReturn(Optional.empty());
        when(productMapper.toEntity(productRequest,category)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toResponseDTO(product)).thenReturn(new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImgUrl(),
                product.getActive(),
                product.getCategory().getName()
        ));

        ProductResponseDTO productResponseDTO = productService.createProduct(productRequest);
        assertNotNull(productResponseDTO);
        assertEquals(productResponseDTO.name(), productRequest.name());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void deveLancarExcecaoQuandoCadastrarProdutoComMesmoNome(){
        when(categoryService.getCategory(categoryRequest.name())).thenReturn(category);
        when(productRepository.findByName(productRequest.name())).thenReturn(Optional.of(product));

        assertThrows(ResourceThisPresentException.class, () -> productService.createProduct(productRequest));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void deveRetornarListaDeProdutosAtivos(){
        Pageable  pageable = PageRequest.of(1,10, Sort.by(Sort.Direction.ASC,"price"));
        Specification<Product>  specification =  Specification.where(ProductSpecifications.isActive());


    }
}