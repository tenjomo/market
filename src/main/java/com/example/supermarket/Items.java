package com.example.supermarket;

import java.util.HashMap;
import java.util.Map;


public class Items {

    private final Map<String, Item> items;


    public Items() {
        items = new HashMap<>();
    }


    public void putItem(final Item item) {
        items.put(item.getCode(), item);
    }

    public Item getItem(final String code) {
        return items.get(code);
    }

}
