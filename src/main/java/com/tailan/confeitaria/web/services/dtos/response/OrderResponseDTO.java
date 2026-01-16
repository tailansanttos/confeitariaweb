package com.tailan.confeitaria.web.services.dtos.response;


import java.time.Instant;
import java.util.Set;

public record OrderResponseDTO(Long id, Instant momentOrder, Long clientId, Set<OrderItemDTO> itens, Integer status) {
}
