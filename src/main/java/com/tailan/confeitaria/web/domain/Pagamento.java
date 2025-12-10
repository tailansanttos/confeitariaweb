package com.tailan.confeitaria.web.domain;

import com.tailan.confeitaria.web.domain.enums.StatusPagamento;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_pagamentos")
public class Pagamento {
    @Id
    private Integer  id;
    private Integer statusPedido;
    private Instant instantePagamento;
    @OneToOne
    @MapsId
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public Pagamento() {

    }

    public Pagamento(Integer id, Instant instantePagamento, Pedido pedido,  Integer statusPedido) {
        this.id = id;
        this.instantePagamento = instantePagamento;
        this.pedido = pedido;
        setStatusPedido(statusPedido);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StatusPagamento getStatusPedido() {
        return StatusPagamento.valueOf(statusPedido);
    }

    public void setStatusPedido(Integer statusPedido) {
        if (statusPedido != null) {
            this.statusPedido = statusPedido;
        }
    }

    public Instant getInstantePagamento() {
        return instantePagamento;
    }

    public void setInstantePagamento(Instant instantePagamento) {
        this.instantePagamento = instantePagamento;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
