package com.tailan.confeitaria.web.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderItemPk implements Serializable {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Order getPedido() {
        return order;
    }

    public void setPedido(Order order) {
        this.order = order;
    }

    public Product getProduto() {
        return product;
    }

    public void setProduto(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemPk)) return false;
        OrderItemPk other = (OrderItemPk) o;
        return Objects.equals(order, other.order) &&
                Objects.equals(product, other.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}
