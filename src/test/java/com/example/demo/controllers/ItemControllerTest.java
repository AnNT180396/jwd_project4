package com.example.demo.controllers;

import com.example.demo.InjectMockUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.controllers.DummyData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;

    @InjectMocks
    private final ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        InjectMockUtils.inject(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void testGetAllItems() {
        List<Item> items = new ArrayList<>(Arrays.asList(getItemDataFirst(), getItemDataSecond()));
        when(itemRepository.findAll()).thenReturn(items);

        ResponseEntity<List<Item>> response = itemController.getItems();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> responseItem = response.getBody();
        assertNotNull(responseItem);
        assertEquals(2, responseItem.size());
    }

    @Test
    public void testGetItemById() {
        Item item = getItemDataFirst();
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item));

        ResponseEntity<Item> response = itemController.getItemById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Item responseItem = response.getBody();
        assertEquals(item, responseItem);
        assertNotNull(responseItem);
        assertEquals(item.getName(), responseItem.getName());
        assertEquals(item.getDescription(), responseItem.getDescription());
    }

    @Test
    public void testGetItemsByName() {
        Item item = getItemDataSecond();
        List<Item> items = new ArrayList<>(Arrays.asList(item));
        when(itemRepository.findByName(FirstItemName)).thenReturn(items);

        ResponseEntity<List<Item>> response = itemController.getItemsByName(FirstItemName);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> responseItem = response.getBody();
        assertNotNull(responseItem);
        assertEquals(item, responseItem.get(0));
    }
}