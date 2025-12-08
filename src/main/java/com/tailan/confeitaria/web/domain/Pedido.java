package com.tailan.confeitaria.web.domain;

import com.tailan.confeitaria.web.domain.enums.Status;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Table(name = "tb_pedidos")
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Instant  momentoPedido;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Pagamento pagamento;

    @OneToMany(mappedBy = "pedido_id")
    private Set<ItemPedido> itens;


    //No banco salva como Inteiro para melhor velocidade(1,2,3,4,5,6)
    private Integer status;

    public Pedido(){

    }

    public Pedido(UUID id, Instant momentoPedido, Integer status) {
        this.id = id;
        this.momentoPedido = momentoPedido;
        this.status = status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Instant getMomentoPedido() {
        return momentoPedido;
    }

    public void setMomentoPedido(Instant momentoPedido) {
        this.momentoPedido = momentoPedido;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Status getStatus(){
        return Status.valueOf(status);
    }

    public void setStatus(Status status){
        if (status != null) {
            this.status = status.getCode();
        }
    }

}
