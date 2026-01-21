package com.tailan.confeitaria.web.controller;

import com.tailan.confeitaria.web.domain.Cart;
import com.tailan.confeitaria.web.services.CartService;
import com.tailan.confeitaria.web.services.dtos.request.CartItemRequest;
import com.tailan.confeitaria.web.services.dtos.response.ApiResponseDTO;
import com.tailan.confeitaria.web.services.dtos.response.CartResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Cart")

public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @Operation(description = "Deve adicionar item ao carrinho do usuário.")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Product not found!"), @ApiResponse(responseCode = "201", description = "Item adicionado ao carrinho.")})
    @PostMapping
    public ResponseEntity<ApiResponseDTO> addItemToCart(Authentication authentication, @RequestBody @Valid CartItemRequest cartItem) {
        String username = authentication.getName();
        CartResponse cart = cartService.addItemToCart(username, cartItem);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cart.id()).toUri();

        ApiResponseDTO response = new ApiResponseDTO(cart, HttpStatus.CREATED.value());
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(description = "Deve atualizar a quantidade de um item no carrinho do usuário.")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Product not found!"), @ApiResponse(responseCode = "200", description = "Item adicionado ao carrinho.")})
    @PutMapping
    public ResponseEntity<ApiResponseDTO> updateItemQuantity(Authentication authentication, @RequestBody @Valid CartItemRequest cartItem) {
       String username = authentication.getName();
        cartService.updateItemQuantity(username, cartItem);
        ApiResponseDTO response = new ApiResponseDTO(null, HttpStatus.OK.value());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    @Operation(description = "Deve remover um item do carrinho do usuário.")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Product not found!"), @ApiResponse(responseCode = "204", description = "Item removido do carrinho.")}) @PutMapping()
    public ResponseEntity<ApiResponseDTO> removeItemFromCart(Authentication authentication, @RequestBody @Valid CartItemRequest cartItem) {
        String username = authentication.getName();
        cartService.removeItemFromCart(username, cartItem);
        ApiResponseDTO response = new ApiResponseDTO(null, HttpStatus.NO_CONTENT.value());
        return ResponseEntity.noContent().build();
    }

    @GetMapping

    @Operation(description = "Deve retornar detalhes do carrinho do usuario.")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Cart not found!"), @ApiResponse(responseCode = "200", description = "Carrinho retornado.")})
    public ResponseEntity<ApiResponseDTO> getCartDetails(Authentication authentication) {

        String username = authentication.getName();
        CartResponse responseCart = cartService.getCartDetails(username);
        ApiResponseDTO response = new ApiResponseDTO(responseCart, HttpStatus.OK.value());
        return ResponseEntity.ok().body(response);
    }
}

