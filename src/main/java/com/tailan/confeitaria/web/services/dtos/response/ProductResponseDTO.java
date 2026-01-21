package com.tailan.confeitaria.web.services.dtos.response;

import java.math.BigDecimal;

public record ProductResponseDTO(Long id, String name, String description, BigDecimal price, String imgUrl, Boolean active, String category, Integer quantity) {
}
