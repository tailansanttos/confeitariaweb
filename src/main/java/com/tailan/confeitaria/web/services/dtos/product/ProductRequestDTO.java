package com.tailan.confeitaria.web.services.dtos.product;

import com.tailan.confeitaria.web.domain.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductRequestDTO(@NotBlank(message = "Campo name é obrigatório") String name,
                                @NotBlank(message = "Campo description é obrigatório")String description,
                                BigDecimal price,
                                @NotBlank(message = "Url da imagem é obrigatório")String imgUrl,
                                Boolean active,
                                @NotBlank(message = "Campo category é obrigatório")String category,
                                @Min(1) Integer quantity) {
}
