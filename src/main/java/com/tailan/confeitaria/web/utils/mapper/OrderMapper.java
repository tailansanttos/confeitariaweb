package com.tailan.confeitaria.web.utils.mapper;

import com.tailan.confeitaria.web.domain.Order;
import com.tailan.confeitaria.web.domain.OrderItem;
import com.tailan.confeitaria.web.services.dtos.response.OrderItemDTO;
import com.tailan.confeitaria.web.services.dtos.response.OrderResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public OrderResponseDTO toOrderResponseDTO(Order savedOrder, Set<OrderItem> orderItems) {

        Set<OrderItemDTO> orderItemDTOS = orderItems.stream().map(item -> new OrderItemDTO(item.getProduct().getId(), item.getProduct().getName(), item.getQuantity(), item.getProduct().getPrice())).collect(Collectors.toSet());
        return new OrderResponseDTO(savedOrder.getId(), savedOrder.getMomentOrder(), savedOrder.getClient().getId(), orderItemDTOS, savedOrder.getStatus().getCode());

    }


}
