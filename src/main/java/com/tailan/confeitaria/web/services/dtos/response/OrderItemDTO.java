package com.tailan.confeitaria.web.services.dtos.response;

import com.tailan.confeitaria.web.domain.Product;

import java.math.BigDecimal;

public record OrderItemDTO(Long productId, String productName, Integer quantity, BigDecimal price) {
}
