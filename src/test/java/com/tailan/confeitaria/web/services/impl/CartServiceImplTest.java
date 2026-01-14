package com.tailan.confeitaria.web.services.impl;

import com.tailan.confeitaria.web.domain.Cart;
import com.tailan.confeitaria.web.domain.CartItem;
import com.tailan.confeitaria.web.domain.Product;
import com.tailan.confeitaria.web.domain.User;
import com.tailan.confeitaria.web.infra.exception.ResourceNotFoundException;
import com.tailan.confeitaria.web.repository.CartRepository;
import com.tailan.confeitaria.web.services.AuthService;
import com.tailan.confeitaria.web.services.ProductService;
import com.tailan.confeitaria.web.services.dtos.request.CartItemRequest;
import com.tailan.confeitaria.web.services.dtos.response.CartResponse;
import com.tailan.confeitaria.web.utils.mapper.CartMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private AuthService authService;
    @Mock
    private ProductService productService;
    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartServiceImpl cartService;

    private User user;
    private Product product;
    private CartItemRequest requestDto;
    private Cart cart;

    private Product product2;
    private CartItem item1;
    private CartItem item2;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setEmail("tailansantt2@gmail.com");

        requestDto = new CartItemRequest(1L, 2);

        product = new Product();
        product.setId(1L);
        product.setQuantity(10);
        product.setPrice(BigDecimal.valueOf(20));

        cart = new Cart();
        cart.setId(50L);
        cart.setUser(user);

        product2 = new Product();
        product2.setId(100L);
        product2.setPrice(BigDecimal.valueOf(30));

        item1 = new CartItem();
        item1.setProduct(product);
        item1.setId(1L);

        item2 = new CartItem();
        item2.setProduct(product2);
        item2.setId(2L);
    }

    @Test
    @DisplayName("Deve criar um novo carrinho quando o usuário ainda não tiver um")
    void addItemToCart_ShouldCreateNewCart_WhenCartDoesNotExist() {
        Cart cartSalvoMock = new Cart();
        cartSalvoMock.setId(5L);

        CartResponse responseEsperado = new CartResponse(5L, user.getEmail(), null, BigDecimal.valueOf(40));

        //Quando pedir o usuário, devolve o User
        when(authService.getUser(user.getEmail())).thenReturn(user);

        when(productService.getProductById(product.getId())).thenReturn(product);

        //Simular que o Carrinho ainda não existe
        when(cartRepository.findCartByUser_Email(user.getEmail())).thenReturn(null);

        //Quando salvar qualquer carrinho, retorna o carrinho simulado
        when(cartRepository.save(any(Cart.class))).thenReturn(cartSalvoMock);

        //quando o mapper for chamado, deve retornar o carinho salvo simulado
        when(cartMapper.toCartResponse(cartSalvoMock)).thenReturn(responseEsperado);

        when(productService.productDisponible(any(Product.class), anyInt())).thenReturn(true);
        CartResponse result = cartService.addItemToCart(user.getEmail(), requestDto);

        assertNotNull(result);
        assertEquals(responseEsperado, result);
        assertEquals(5L, result.id());

        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    @DisplayName("Deve adicionar item ao Carrinho que ja existe pra um usuario")
    void addItemToCart_WhenCartExist(){
        CartResponse responseEsperado = new CartResponse(cart.getId(), cart.getUser().getEmail(), null, BigDecimal.valueOf(40));

        when(authService.getUser(user.getEmail())).thenReturn(user);
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(cartRepository.findCartByUser_Email(user.getEmail())).thenReturn(cart);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(cartMapper.toCartResponse(cart)).thenReturn(responseEsperado);
        when(productService.productDisponible(any(Product.class), anyInt())).thenReturn(true);

        CartResponse result = cartService.addItemToCart(user.getEmail(), requestDto);
        assertNotNull(result);
        assertEquals(responseEsperado, result);
        assertEquals(50L, result.id());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    @DisplayName("Erro de estoque insuficiente")
    void addItemToCart_WhenProductEstoqueInsuficiente(){
        product.setQuantity(1);

        CartResponse responseEsperado = new CartResponse(cart.getId(), cart.getUser().getEmail(), null, BigDecimal.valueOf(40));
        when(authService.getUser(user.getEmail())).thenReturn(user);
        when(productService.getProductById(product.getId())).thenReturn(product);

        assertThrows(ResourceNotFoundException.class, () -> cartService.addItemToCart(user.getEmail(), requestDto));
        verify(cartRepository, never()).save(any(Cart.class));

    }

    @Test
    @DisplayName("Deve remover um item do carrinho")
    void removeItemToCart(){
        CartItemRequest requestRemoveDto = new CartItemRequest(1L, 1);

        CartItem cartItem = new CartItem();
        cartItem.setId(5L);
        cartItem.setProduct(product);

        cart.addItem(cartItem);

        when(cartRepository.findCartByUser_Email(user.getEmail())).thenReturn(cart);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        cartService.removeItemFromCart(user.getEmail(), requestRemoveDto);

        assertEquals(0, cart.getItems().size());
        verify(cartRepository, times(1)).save(any(Cart.class));

    }

    @Test
    @DisplayName("Deve falhar ao remover um item que não está no carrinho")
    void ItShouldFailToRemoveAnItemThatIsNotInTheCart(){
        User userTestFail = new User();
        userTestFail.setId(5L);
        userTestFail.setEmail("tailan2@gmail.com");

        CartItemRequest requestRemoveDto = new CartItemRequest(1L, 1);
        Cart cartTestFail = new Cart();
        cartTestFail.setId(5L);
        cartTestFail.setUser(userTestFail);

        when(cartRepository.findCartByUser_Email(userTestFail.getEmail())).thenReturn(cartTestFail);
        assertThrows(ResourceNotFoundException.class, () -> cartService.removeItemFromCart(userTestFail.getEmail(), requestRemoveDto));
        verify(cartRepository, never()).save(any(Cart.class));
    }


    @Test
    @DisplayName("Deve calcular totais do carrinho")
    void ItShouldCalculateTotais(){

        item1.setQuantity(5);
        item2.setQuantity(3);

        cart.addItem(item1);
        cart.addItem(item2);

        when(cartRepository.findCartByUser_Email(user.getEmail())).thenReturn(cart);
        List<CartItemRequest> itemsToCart = cart.getItems()
                .stream().map(item -> new CartItemRequest(item.getProduct().getId(), item.getQuantity()))
                .toList();

        CartResponse responseEsperado = new CartResponse(cart.getId(), cart.getUser().getEmail(), itemsToCart, BigDecimal.valueOf(190));

        CartResponse response = cartService.getCartDetails(user.getEmail());
        assertEquals(responseEsperado, response);
        assertEquals(responseEsperado.total(), response.total());

    }

    @Test
    @DisplayName("Deve atualizar quantidade do produto no carrinho para zero")
    void updateItemQuantityToZero(){
        item1.setQuantity(5);
        cart.addItem(item1);

        CartItemRequest requestUpdate = new CartItemRequest(1L, 0);

        when(cartRepository.findCartByUser_Email(user.getEmail())).thenReturn(cart);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        cartService.updateItemQuantity(user.getEmail(), requestUpdate);
        assertEquals(0, cart.getItems().size());
        verify(cartRepository, times(1)).save(any(Cart.class));


    }


}