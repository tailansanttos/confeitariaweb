package com.tailan.confeitaria.web.services.dtos.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

public record OrdersClientDTO(Long id, Instant date, String status, BigDecimal value) {
}
