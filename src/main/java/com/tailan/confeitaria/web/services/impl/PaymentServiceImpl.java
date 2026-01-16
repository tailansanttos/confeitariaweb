package com.tailan.confeitaria.web.services.impl;

import com.tailan.confeitaria.web.domain.Order;
import com.tailan.confeitaria.web.domain.Payment;
import com.tailan.confeitaria.web.domain.enums.OrderStatus;
import com.tailan.confeitaria.web.domain.enums.StatusPayment;
import com.tailan.confeitaria.web.repository.PaymentRepository;
import com.tailan.confeitaria.web.services.OrderService;
import com.tailan.confeitaria.web.services.PaymentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }


    @Override
    @Transactional
    public void makePayment(Long orderId, String userEmail) {
        Order order = orderService.getOrderByClient(userEmail, orderId);

        Payment payment = order.getPayment();

        if (order.getStatus().equals(OrderStatus.CANCELED)){
            throw new IllegalArgumentException("Order canceled.");
        }

        order.setStatus(OrderStatus.PAID);
        payment.setStatusPayment(StatusPayment.PAID_OFF.getCode());
        payment.setInstantPayment(Instant.now());

        paymentRepository.save(payment);

    }

    @Override
    public Payment createPayment(Order order) {
        Payment payment = new Payment();
        payment.setInstantPayment(Instant.now());
        payment.setStatusPayment(StatusPayment.PENDING.getCode());
        payment.setOrder(order);

        paymentRepository.save(payment);
        return payment;
    }
}
