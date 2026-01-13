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
                .map(itemDto -> toCartItem(user,product,cartItemRequest.quantity())).collect(Collectors.toList());
        return cartItems;

    }



    public CartItem toCartItem(User user, Product product, Integer quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setQuantity(quantity);
        cartItem.setProduct(product);
        return cartItem;
    }

    public CartResponse toCartResponse(Cart cart) {
        return new CartResponse(cart.getId(), cart.getUser().getEmail(),cart.getItems().stream().map(itens -> new CartItemRequest(itens.getId(), itens.getQuantity())).collect(Collectors.toList()), BigDecimal.ZERO);
    }
}
