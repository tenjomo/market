package com.example.supermarket;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import org.junit.Test;

public class ItemsTest {

    @Test
    public void whenItemIsPut_ThenItCanBeRetrievedByCode() {
        Items items = new Items();
        String itemCode = "Banana";
        Item banana = new Item(itemCode, BigDecimal.valueOf(0.25));
        items.putItem(banana);
        assertEquals(banana, items.getItem(itemCode));
    }

}
