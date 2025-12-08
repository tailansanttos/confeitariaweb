package com.tailan.confeitaria.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
@Table(name = "tb_item_pedidos")
public class ItemPedido {

    @EmbeddedId
    private ItemPedidoPk id = new ItemPedidoPk();

    private Integer quantidade;
    private Double preco;
    public ItemPedido() {

    }

    public ItemPedido(Pedido pedido, Produto produto, Integer quantidade, Double preco) {
        id.setPedido(pedido);
        id.setProduto(produto);
        this.quantidade = quantidade;
        this.preco = preco;
    }

    @JsonIgnore
    private Pedido getPedido(){
        return id.getPedido();
    };

    private Produto getProduto(){
        return id.getProduto();
    }
}
