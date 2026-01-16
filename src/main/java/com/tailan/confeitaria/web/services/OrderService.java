package com.tailan.confeitaria.web.services;

import com.tailan.confeitaria.web.domain.Order;
import com.tailan.confeitaria.web.domain.enums.OrderStatus;
import com.tailan.confeitaria.web.services.dtos.response.OrderResponseDTO;
import com.tailan.confeitaria.web.services.dtos.response.OrdersClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

public interface OrderService {
    OrderResponseDTO completePurchase(String userEmail);

    Page<OrdersClientDTO> getOrders(String userEmail, int page, int size, String sortBy, String direction);

    OrderResponseDTO getOrderById(String userEmail, Long orderId);

    void cancelOrder(String userEmail, Long orderId);

    void updateOrderStatus(Long orderId, OrderStatus orderStatus);

    // Mudar o status do pedido para cancelado.
    void executeCancellationInternal(Order order);

    Order getOrderByClient(String userEmail, Long orderId);
}
