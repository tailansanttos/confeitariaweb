package com.tailan.confeitaria.web.services.dtos.response;

import com.tailan.confeitaria.web.services.dtos.request.CartItemRequest;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(Long id, String userEmail, List<CartItemRequest> items,BigDecimal total) {
}
