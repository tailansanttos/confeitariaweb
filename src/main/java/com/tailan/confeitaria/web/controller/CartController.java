package com.tailan.confeitaria.web.controller;

import com.tailan.confeitaria.web.domain.Cart;
import com.tailan.confeitaria.web.services.CartService;
import com.tailan.confeitaria.web.services.dtos.request.CartItemRequest;
import com.tailan.confeitaria.web.services.dtos.response.ApiResponseDTO;
import com.tailan.confeitaria.web.services.dtos.response.CartResponse;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping
    public ResponseEntity<ApiResponseDTO> addItemToCart(Authentication authentication, @RequestBody @Valid CartItemRequest cartItem) {
        String username = authentication.getName();
        CartResponse cart = cartService.addItemToCart(username, cartItem);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cart.id()).toUri();

        ApiResponseDTO response = new ApiResponseDTO(cart, HttpStatus.CREATED.value());
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping()
    public ResponseEntity<ApiResponseDTO> updateItemQuantity(Authentication authentication, @RequestBody @Valid CartItemRequest cartItem) {
       String username = authentication.getName();
        cartService.updateItemQuantity(username, cartItem);
        ApiResponseDTO response = new ApiResponseDTO(null, HttpStatus.OK.value());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponseDTO> removeItemFromCart(Authentication authentication, @RequestBody @Valid CartItemRequest cartItem) {
        String username = authentication.getName();
        cartService.removeItemFromCart(username, cartItem);
        ApiResponseDTO response = new ApiResponseDTO(null, HttpStatus.NO_CONTENT.value());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO> getCartDetails(Authentication authentication) {

        String username = authentication.getName();
        CartResponse responseCart = cartService.getCartDetails(username);
        ApiResponseDTO response = new ApiResponseDTO(responseCart, HttpStatus.OK.value());
        return ResponseEntity.ok().body(response);
    }
}

