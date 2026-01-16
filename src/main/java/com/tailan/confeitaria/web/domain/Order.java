package com.tailan.confeitaria.web.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.tailan.confeitaria.web.domain.enums.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
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


    @Column(name = "status")
    private Integer status;

    public Order() {}


    public Order(Long id, Instant momentOrder, User client, Payment payment, Set<OrderItem> itens, Integer status) {
        this.id = id;
        this.momentOrder = momentOrder;
        this.client = client;
        this.payment = payment;
        this.itens = itens;
        this.status = status;
    }

    @JsonProperty("status")
    public OrderStatus getStatus() {
        return OrderStatus.valueOf(status);
    }

    @JsonProperty("status")
    public void setStatus(OrderStatus orderStatus) {
        this.status = orderStatus.getCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMomentOrder() {
        return momentOrder;
    }

    public void setMomentOrder(Instant momentOrder) {
        this.momentOrder = momentOrder;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Set<OrderItem> getItens() {
        return itens;
    }

    public void setItens(Set<OrderItem> itens) {
        this.itens = itens;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return itens.stream().map(OrderItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}