package com.tailan.confeitaria.web.services.impl;

import com.tailan.confeitaria.web.domain.Cart;
import com.tailan.confeitaria.web.domain.CartItem;
import com.tailan.confeitaria.web.domain.Product;
import com.tailan.confeitaria.web.domain.User;
import com.tailan.confeitaria.web.infra.exception.ResourceNotFoundException;
import com.tailan.confeitaria.web.repository.CartRepository;
import com.tailan.confeitaria.web.services.AuthService;
import com.tailan.confeitaria.web.services.CartService;
import com.tailan.confeitaria.web.services.ProductService;
import com.tailan.confeitaria.web.services.dtos.request.CartItemRequest;
import com.tailan.confeitaria.web.services.dtos.response.CartResponse;
import com.tailan.confeitaria.web.utils.mapper.CartMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final ProductService productService;
    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, AuthService authService, ProductService productService, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.authService = authService;
        this.productService = productService;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartResponse addItemToCart(String userEmail, CartItemRequest cartItem) {
        User user = authService.getUser(userEmail);
        Product product = productValid(cartItem);

        //Busca ou cria um carrinho, se nao existir um carrinho pro usuario a gente cria um e seta o user. Caso já exista so colocamos o product, quantity, o USER nao precisa pq ele ja teria um carrinho
        Cart cart = cartRepository.findCartByUser_Email(userEmail);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }
        CartItem newItem = new CartItem();
        newItem.setProduct(product);
        newItem.setQuantity(cartItem.quantity());

        newItem.setCart(cart);
        cart.addItem(newItem);

        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toCartResponse(savedCart);
    }

    @Override
    public void removeItemFromCart(String userEmail, CartItemRequest cartItemRequest) {
        Cart cart = getCartToUser(userEmail);
        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(cartItemRequest.productId())).findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found in the cart."));

        cart.removeItem(itemToRemove);
        cartRepository.save(cart);

    }

    @Override
    public void updateItemQuantity(String userEmail, CartItemRequest cartItem) {
        Cart cart = getCartToUser(userEmail);
        CartItem itemToUpdate = cart.getItems().stream().filter(
                item -> item.getProduct().getId().equals(cartItem.productId())).findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found in the cart."));

        if (cartItem.quantity() <= 0 ){
            cart.removeItem(itemToUpdate);
            cartRepository.save(cart);
            return;
        }
        if (itemToUpdate.getProduct().getQuantity() < cartItem.quantity()){
            throw new IllegalArgumentException("Quantity must be greater than or equal to the item quantity");
        }

        itemToUpdate.setQuantity(cartItem.quantity());
        cartRepository.save(cart);


    }

    @Override
    public CartResponse getCartDetails(String userEmail) {
        Cart cart = getCartToUser(userEmail);
        List<CartItemRequest> itemsToCart = cart.getItems()
                .stream().map(item -> new CartItemRequest(item.getProduct().getId(), item.getQuantity()))
                .collect(Collectors.toList());

        BigDecimal totalCart = new BigDecimal(0);
        //Percorre os itens do carrinho e calcular o total e subtotal
        for (CartItem cartItem : cart.getItems()) {
            BigDecimal price = cartItem.getProduct().getPrice();
            BigDecimal quantity = BigDecimal.valueOf(cartItem.getQuantity());

            BigDecimal subTotal = price.multiply(quantity);
            totalCart = totalCart.add(subTotal);

        }

        return new CartResponse(cart.getId(), cart.getUser().getEmail(), itemsToCart, totalCart);


    }

    private Product productValid(CartItemRequest cartItem) {
        Product product = productService.getProductById(cartItem.productId());
        Boolean productDisponible =  productService.productDisponible(product, cartItem.quantity());

        if (!productDisponible) {
            throw new ResourceNotFoundException("Product not disponible this quantity.");
        }
        return product;
    }

    @Override
    public Cart getCartToUser(String userEmail) {
        Cart cart = cartRepository.findCartByUser_Email(userEmail);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found.");
        }
        return cart;
    }

    @Override
    public void clearCart(String userEmail) {
        Cart cart = getCartToUser(userEmail);
        if (cart.getItems().isEmpty()) {
            throw new ResourceNotFoundException("Cart items not found.");
        }
        cart.getItems().clear();
        cartRepository.save(cart);

    }

    CartItem savedCartItem(CartItemRequest cartItem, Cart cart) {
        CartItem cartItemToReturn = new CartItem();
        Product product = productService.getProductById(cartItem.productId());
        User user = authService.getUser(cart.getUser().getEmail());
        cartItemToReturn.setCart(cart);
        cartItemToReturn.setProduct(product);
        cartItemToReturn.setQuantity(cartItem.quantity());
        return cartItemToReturn;
    }
}
