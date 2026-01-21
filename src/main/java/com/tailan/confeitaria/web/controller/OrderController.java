package com.tailan.confeitaria.web.controller;

import com.tailan.confeitaria.web.services.OrderService;
import com.tailan.confeitaria.web.services.dtos.response.ApiResponseDTO;
import com.tailan.confeitaria.web.services.dtos.response.OrderResponseDTO;
import com.tailan.confeitaria.web.services.dtos.response.OrdersClientDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.juli.logging.Log;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/orders")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Order")

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO> comletePurchase(Authentication authentication){
        OrderResponseDTO responseDTO = orderService.completePurchase(authentication.getName());
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(responseDTO, HttpStatus.CREATED.value());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(responseDTO.id()).toUri();

        return ResponseEntity.created(uri).body(apiResponseDTO);
    }


    @GetMapping
    public ResponseEntity<ApiResponseDTO> getOrdersByClient(Authentication authentication,
                                                            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                            @RequestParam(value = "size", defaultValue = "10", required = false)int size,
                                                            @RequestParam(value = "orderBy", defaultValue = "momentOrder", required = false) String sortBy,
                                                            @RequestParam(value = "direction", defaultValue = "ASC", required = false)String direction){
        Page<OrdersClientDTO> ordersClientDTOS = orderService.getOrders(authentication.getName(), page, size, sortBy, direction);
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(ordersClientDTOS, HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponseDTO);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponseDTO> getOrderById(@PathVariable("orderId") Long orderId, Authentication authentication){
        OrderResponseDTO orderResponseDTO = orderService.getOrderById(authentication.getName(), orderId);
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(orderResponseDTO, HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponseDTO);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseDTO> deleteOrderById(@PathVariable("orderId") Long orderId, Authentication authentication){
        orderService.cancelOrder(authentication.getName(), orderId);
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(orderId, HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponseDTO);
    }
}
