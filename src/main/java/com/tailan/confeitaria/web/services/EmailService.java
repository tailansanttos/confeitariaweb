package com.tailan.confeitaria.web.services;

import com.tailan.confeitaria.web.domain.Order;

public interface EmailService {

    void sendOrderConfirmationEmail(Order order);

}

