package com.example.demo.controllers;

import com.example.demo.InjectMockUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    public static final String USERNAME = "annt";
    private OrderController orderController;
    private final UserRepository userRepository = mock(UserRepository.class);
    private final OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        InjectMockUtils.inject(orderController, "userRepository", userRepository);
        InjectMockUtils.inject(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void testSubmit() {
        User user = DummyData.getUser();
        Cart cart = DummyData.getCart();
        user.setCart(cart);
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        ResponseEntity<UserOrder> response = orderController.submit(USERNAME);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        UserOrder responseOrder = response.getBody();
        assertNotNull(responseOrder);
        assertNotNull(responseOrder.getItems());
        assertNotNull(responseOrder.getUser());
    }

    @Test
    public void testSubmitUserIsNull() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(null);

        ResponseEntity<UserOrder> response = orderController.submit(USERNAME);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testGetOrdersForUser() {
        User user = DummyData.getUser();
        Cart cart = DummyData.getCart();
        user.setCart(cart);
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        orderController.submit(USERNAME);

        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser(USERNAME);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        List<UserOrder> userOrders = responseEntity.getBody();
        assertNotNull(userOrders);
    }

    @Test
    public void testGetOrdersForUserIsNull() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(null);

        orderController.submit(USERNAME);

        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser(USERNAME);

        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }
}