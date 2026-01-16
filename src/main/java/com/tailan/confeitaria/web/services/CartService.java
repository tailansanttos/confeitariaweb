package com.tailan.confeitaria.web.services;

import com.tailan.confeitaria.web.domain.Cart;
import com.tailan.confeitaria.web.services.dtos.request.CartItemRequest;
import com.tailan.confeitaria.web.services.dtos.response.CartResponse;

public interface CartService {
    CartResponse addItemToCart(String userEmail, CartItemRequest cartItem);
    void removeItemFromCart(String userEmail, CartItemRequest cartItem);
    void updateItemQuantity(String userEmail, CartItemRequest cartItem);
    CartResponse getCartDetails(String userEmail);

    Cart getCartToUser(String userEmail);

    void clearCart(String userEmail);
}
