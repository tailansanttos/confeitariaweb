package com.tailan.confeitaria.web.services.dtos.category;

import com.tailan.confeitaria.web.domain.Product;

import java.util.Set;

public record CategoryResponseDTO(Long id, String name, Set<Product> products) {
}
