package com.tailan.confeitaria.web.services.impl;

import com.tailan.confeitaria.web.domain.Cart;
import com.tailan.confeitaria.web.domain.Order;
import com.tailan.confeitaria.web.domain.OrderItem;
import com.tailan.confeitaria.web.domain.Payment;
import com.tailan.confeitaria.web.domain.enums.OrderStatus;
import com.tailan.confeitaria.web.infra.exception.ResourceNotFoundException;
import com.tailan.confeitaria.web.repository.OrderItemRepository;
import com.tailan.confeitaria.web.repository.OrderRepository;
import com.tailan.confeitaria.web.services.CartService;
import com.tailan.confeitaria.web.services.OrderService;
import com.tailan.confeitaria.web.services.PaymentService;
import com.tailan.confeitaria.web.services.ProductService;
import com.tailan.confeitaria.web.services.dtos.response.OrderResponseDTO;
import com.tailan.confeitaria.web.services.dtos.response.OrdersClientDTO;
import com.tailan.confeitaria.web.utils.mapper.OrderMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final PaymentService paymentService;

    public OrderServiceImpl(ProductService productService, OrderRepository orderRepository, CartService cartService, OrderItemRepository orderItemRepository, OrderMapper orderMapper, PaymentService paymentService) {
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
        this.paymentService = paymentService;
    }

    @Transactional
    @Override
    public OrderResponseDTO completePurchase(String userEmail) {
        Cart cart = cartService.getCartToUser(userEmail);
        if (cart.getItems().isEmpty()) {
            throw new ResourceNotFoundException("Cannot complete purchase with empty cart.");
        }

        Order order = new Order();
        order.setMomentOrder(Instant.now());
        order.setStatus(OrderStatus.AWAITING_PAYMENT);
        order.setClient(cart.getUser());

        Order savedOrder = orderRepository.save(order);

        Set<OrderItem> orderItems = createOrderItem(cart, savedOrder);
        orderItemRepository.saveAll(orderItems);

        savedOrder.setItens(orderItems);
        Payment payment = paymentService.createPayment(savedOrder);
        order.setPayment(payment);
        orderRepository.save(order);


        cartService.clearCart(userEmail);

        return orderMapper.toOrderResponseDTO(savedOrder, orderItems);
    }

    @Override
    public Page<OrdersClientDTO> getOrders(String userEmail, int page, int size, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortBy);

        Page<Order> orders = orderRepository.findAllByClient_Email(userEmail, pageable);

        Page<OrdersClientDTO> response = orders.map(
                order -> new OrdersClientDTO(order.getId(),
                        order.getMomentOrder(), order.getStatus().toString(), order.getTotal()));
        return response;
    }

    @Override
    public OrderResponseDTO getOrderById(String userEmail, Long orderId) {
        Order order = getOrderByClient(userEmail, orderId);

        return orderMapper.toOrderResponseDTO(order, order.getItens());

    }

    @Override
    @Transactional
    public void cancelOrder(String userEmail, Long orderId) {
        Order order = getOrderByClient(userEmail, orderId);

        executeCancellationInternal(order);
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order with id " + orderId + " not found."));
        if (order.getStatus().equals(OrderStatus.CANCELED) && orderStatus.equals(OrderStatus.DELIVERED)) {
            throw new ResourceNotFoundException("Cannot cancel order with status " + orderStatus.toString());
        }
        order.setStatus(orderStatus);
    }


    // Mudar o status do pedido para cancelado.
    @Override
    public void executeCancellationInternal(Order order) {

        order.getItens().forEach(orderItem ->
        { productService.updateStock(orderItem.getProduct().getId(), orderItem.getProduct().getQuantity());
        }
        );
        order.setStatus(OrderStatus.CANCELED);

        orderRepository.save(order);
    }


    Set<OrderItem> createOrderItem(Cart cart, Order order) {
        return cart.getItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem
                    (order,
                            cartItem.getProduct(),
                            cartItem.getQuantity(),
                            cartItem.getProduct().getPrice()
                    );
            productService.reduceStock(cartItem.getProduct().getId(), cartItem.getQuantity());
            return orderItem;
        }).collect(Collectors.toSet());
    }


    @Override
    public Order getOrderByClient(String userEmail, Long orderId) {
        Order order = orderRepository.findByClient_EmailAndId(userEmail, orderId);
        if (order == null) throw new ResourceNotFoundException("Cannot find order with id " + orderId);
        return order;
    }

}
