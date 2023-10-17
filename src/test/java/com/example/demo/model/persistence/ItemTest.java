package com.example.demo.model.persistence;

import com.example.demo.controllers.DummyData;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    @Test
    public void testNotEquals() {
        Item item1 = DummyData.getItemDataFirst();
        assertNotEquals(item1, null);
        Item item2 = DummyData.getItemDataFirst();
        assertNotEquals(item2, null);
        assertNotEquals(item1, item2);
    }

    @Test
    public void testEquals() {
        Item item1 = new Item();
        item1.setId(2L);
        Item item2 = new Item();
        item2.setId(2L);
        assertEquals(item2.getId(), item1.getId());
        assertTrue(item1.getId() == item2.getId());
    }}