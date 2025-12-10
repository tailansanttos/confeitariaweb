package com.tailan.confeitaria.web.domain;

import com.tailan.confeitaria.web.domain.enums.StatusPagamento;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "tb_pagamentos")
public class Pagamento {
    @Id
    private Long  id;

    @Column(name = "status_pagamento")
    private Integer statusPagamento;

    @Column(name = "instante_pagamento")
    private Instant instantePagamento;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public Pagamento() {

    }

    public Pagamento(Long id, Instant instantePagamento, Pedido pedido, Integer statusPagamento) {
        this.id = id;
        this.instantePagamento = instantePagamento;
        this.pedido = pedido;
        setStatusPagamento(statusPagamento);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusPagamento getStatusPedido() {
        return StatusPagamento.valueOf(statusPagamento);
    }

    public void setStatusPagamento(Integer statusPedido) {
        if (statusPedido != null) {
            this.statusPagamento = statusPedido;
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
