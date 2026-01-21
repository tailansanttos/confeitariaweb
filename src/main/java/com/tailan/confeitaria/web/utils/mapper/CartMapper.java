package com.tailan.confeitaria.web.utils.mapper;

import com.tailan.confeitaria.web.domain.Cart;
import com.tailan.confeitaria.web.domain.CartItem;
import com.tailan.confeitaria.web.domain.Product;
import com.tailan.confeitaria.web.domain.User;
import com.tailan.confeitaria.web.services.dtos.request.CartItemRequest;
import com.tailan.confeitaria.web.services.dtos.response.CartResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {
    public List<CartItem> toCartItems(CartItemRequest cartItemRequest, User user, Product product, Cart cart) {
        List<CartItem> cartItems = cart.getItems().stream()
                .map(itemDto -> toCartItem(product,cartItemRequest.quantity(), cart)).collect(Collectors.toList());
        return cartItems;

    }



    public CartItem toCartItem(Product product, Integer quantity, Cart cart) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setQuantity(quantity);
        cartItem.setProduct(product);
        return cartItem;
    }

    public CartResponse toCartResponse(Cart cart) {
        return new CartResponse(cart.getId(), cart.getUser().getEmail(),cart.getItems().stream().map(itens -> new CartItemRequest(itens.getId(), itens.getQuantity())).collect(Collectors.toList()), BigDecimal.ZERO);
    }
}
