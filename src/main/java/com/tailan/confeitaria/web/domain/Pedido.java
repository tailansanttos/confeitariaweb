package com.tailan.confeitaria.web.domain;

import com.tailan.confeitaria.web.domain.enums.Status;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Set;

@Table(name = "tb_pedidos")
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "momento_pedido")
    private Instant  momentoPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Pagamento pagamento;

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itens;


    //No banco salva como Inteiro para melhor velocidade(1,2,3,4,5,6)
    private Integer status;

    public Pedido(){

    }

    public Pedido(Long id, Instant momentoPedido, Integer status) {
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

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
