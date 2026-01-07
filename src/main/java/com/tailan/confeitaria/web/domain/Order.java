package com.tailan.confeitaria.web.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.tailan.confeitaria.web.domain.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Set;

@Table(name = "tb_orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant momentOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private User client;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderItem> itens;

    // 1. Mantenha o campo como Integer para o JPA
    @Column(name = "status")
    private Integer status;

    public Order() {}

    @JsonProperty("status")
    public OrderStatus getStatus() {
        return OrderStatus.valueOf(status);
    }

    @JsonProperty("status")
    public void setStatus(OrderStatus orderStatus) {
        if (orderStatus != null) {
            this.status = orderStatus.getCode();
        }
    }

    // Getters e Setters dos outros campos...
}