package com.tailan.confeitaria.web.services.impl;

import com.tailan.confeitaria.web.domain.Order;
import com.tailan.confeitaria.web.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String sender;

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendOrderConfirmationEmail(Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nao-responda@tailanconfeitaria.com");
        message.setTo(order.getClient().getEmail());
        message.setSubject("Order Confirmation! Code: " + order.getId());

        StringBuilder messageText = new StringBuilder();
        messageText.append("Olá, ").append(order.getClient().getName()).append("! \n");
        messageText.append("Recebemos seu pedido número #").append(order.getId()).append("\n");
        messageText.append("Valor Total: R$ ").append(order.getTotal()).append("\n");
        messageText.append("Status atual: ").append(order.getStatus()).append("\n");
        messageText.append("Aguarde a confirmação do pagamento.");

        message.setText(messageText.toString());

        try {
            mailSender.send(message);
            System.out.println("Email enviado com sucesso!");
        }catch (Exception e) {
            System.err.println("Falha ao enviar email!" + e.getMessage());
        }

    }
}
