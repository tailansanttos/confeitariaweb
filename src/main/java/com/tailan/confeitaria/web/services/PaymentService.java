package com.tailan.confeitaria.web.services;

import com.tailan.confeitaria.web.domain.Order;
import com.tailan.confeitaria.web.domain.Payment;

public interface PaymentService {
    void makePayment(Long orderId, String userEmail);

    Payment createPayment(Order order);
}
