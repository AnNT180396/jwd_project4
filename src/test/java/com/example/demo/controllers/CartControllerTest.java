package com.example.demo.controllers;

import com.example.demo.InjectMockUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    public static final String USERNAME = "annt";
    private CartController cartController;
    private final UserRepository userRepository = mock(UserRepository.class);
    private final CartRepository cartRepository = mock(CartRepository.class);
    private final ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController(userRepository, cartRepository, itemRepository);
        InjectMockUtils.inject(cartController, "cartRepository", cartRepository);
        InjectMockUtils.inject(cartController, "userRepository", userRepository);
        InjectMockUtils.inject(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void testAddToCart() {
        User user = DummyData.getUser();
        Item item = DummyData.getItemDataSecond();
        Cart cart =  DummyData.getCart();
        user.setCart(cart);
        // cart of user size = 2

        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
        when(itemRepository.findById(2L)).thenReturn(java.util.Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(2L);
        request.setQuantity(2);
        request.setUsername(USERNAME);

        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart responseCart = response.getBody();
        assertNotNull(responseCart);
        assertEquals(1L, responseCart.getId());
        List<Item> items = responseCart.getItems();
        assertNotNull(items);
        assertEquals(4, items.size());
        assertEquals(item, items.get(2));
        assertEquals(item.getPrice(), items.get(2).getPrice());
        assertNotEquals(user.getCart(), responseCart.getUser().getCart());
    }

    @Test
    public void testAddToCartUserIsNull() {
        User user = DummyData.getUser();
        Item item = DummyData.getItemDataSecond();
        Cart cart =  DummyData.getCart();
        user.setCart(cart);
        // cart of user size = 2

        when(userRepository.findByUsername(USERNAME)).thenReturn(null);
        when(itemRepository.findById(2L)).thenReturn(java.util.Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(2L);
        request.setQuantity(2);
        request.setUsername(USERNAME);

        ResponseEntity<Cart> response = cartController.addTocart(request);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void removeFromCart() {
        User user = DummyData.getUser();
        Item item = DummyData.getItemDataSecond();
        Cart cart =  DummyData.getCart();
        user.setCart(cart);
        // cart of user size = 2

        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
        when(itemRepository.findById(2L)).thenReturn(java.util.Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(2L);
        request.setQuantity(1);
        request.setUsername(USERNAME);

        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart responseCart = response.getBody();
        assertNotNull(responseCart);
        assertEquals(1L, responseCart.getId());
        List<Item> items = responseCart.getItems();
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals(new BigDecimal("999"), responseCart.getTotal());
        assertNotEquals(user.getCart(), responseCart.getUser().getCart());
    }
}