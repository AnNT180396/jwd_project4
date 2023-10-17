package com.example.demo.model.persistence;

import com.example.demo.controllers.DummyData;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class UserOrderTest {

    @Test
    public void testCreateFromCart() {
        Cart cart = DummyData.getCart();
        UserOrder userOrder = new UserOrder();
        userOrder.setItems(cart.getItems().stream().collect(Collectors.toList()));
        userOrder.setTotal(cart.getTotal());
        userOrder.setUser(cart.getUser());

        UserOrder order = UserOrder.createFromCart(cart);
        assertEquals(userOrder.getId(), order.getId());
    }
}