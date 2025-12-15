package com.tailan.confeitaria.web.domain;

import com.tailan.confeitaria.web.domain.enums.StatusPayment;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "tb_payments")
public class Payment {
    @Id
    private Long  id;

    @Column(name = "status_payment")
    private Integer statusPayment;

    @Column(name = "instant_payment")
    private Instant instantPayment;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pedido_id")
    private Order order;

    public Payment() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusPayment getStatusPedido() {
        return StatusPayment.valueOf(statusPayment);
    }

    public void setStatusPagamento(Integer statusPedido) {
        if (statusPedido != null) {
            this.statusPayment = statusPedido;
        }
    }

    public Integer getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(Integer statusPayment) {
        this.statusPayment = statusPayment;
    }

    public Instant getInstantPayment() {
        return instantPayment;
    }

    public void setInstantPayment(Instant instantPayment) {
        this.instantPayment = instantPayment;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Payment(Long id, Integer statusPayment, Instant instantPayment, Order order) {
        this.id = id;
        this.statusPayment = statusPayment;
        this.instantPayment = instantPayment;
        this.order = order;
    }
}
