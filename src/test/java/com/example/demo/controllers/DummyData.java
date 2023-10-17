package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class DummyData {

    public static String FirstItemName = "Iphone 15";
    public static String SecondItemName = "Samsung Galaly S23 Ultra";
    public static Item getItemDataFirst() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Iphone 15");
        item.setPrice(new BigDecimal("999"));
        item.setDescription("A new Iphone");
        return item;
    }

    public static Item getItemDataSecond() {
        Item item = new Item();
        item.setId(2L);
        item.setName("Samsung Galaly S23 Ultra");
        item.setPrice(new BigDecimal("1999"));
        item.setDescription("A new Galaxy");
        return item;
    }

    public static Cart getCart() {

        Item item1 = getItemDataFirst();
        Item item2 = getItemDataSecond();


        User user = new User();
        user.setId(1);
        user.setPassword("abc1234");
        user.setUsername("annt");

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setItems(new ArrayList<>(Arrays.asList(item1, item2)));
        cart.setTotal(new BigDecimal("2998"));

        return cart;
    }
    public static User getUser() {
        String username = "annt";
        User user = new User();
        user.setUsername(username);
        String password = "password";
        user.setPassword(password);
        user.setId(999L);
        return user;
    }
}
