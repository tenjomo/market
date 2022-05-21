package com.example.supermarket;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

public class BasketTest {

    private Basket basket;

    @Before
    public void setUp() {
        basket = new Basket();
        basket.addItem("Banana");
        basket.addItem("Orange Juice");
        basket.addWeightedItem("Potatoes", BigDecimal.valueOf(1.5));
    }

    @Test
    public void whenNewItemIsAdded_ThenQuantityIsOne() {
        basket.addItem("Bran Flakes");
        int quantity = basket.getItems()
                             .get("Bran Flakes");
        assertEquals(1, quantity);
    }

    @Test
    public void whenExistingItemIsAdded_ThenQuantityIsIncremented() {
        basket.addItem("Banana", 2);
        int quantity = basket.getItems()
                             .get("Banana");
        assertEquals(3, quantity);
    }

    @Test
    public void whenNewWeightedItemIsAdded_ThenWeightIsCorrect() {
        basket.addWeightedItem("Onions", BigDecimal.valueOf(0.4));
        BigDecimal weight = basket.getWeightedItems()
                                  .get("Onions");
        assertEquals(0, weight.compareTo(BigDecimal.valueOf(0.4)));
    }

    @Test
    public void whenExistingWeightedItemIsAdded_ThenWeightIsIncreasedCorrectly() {
        basket.addWeightedItem("Potatoes", BigDecimal.valueOf(0.25));
        BigDecimal weight = basket.getWeightedItems()
                                  .get("Potatoes");
        assertEquals(0, weight.compareTo(BigDecimal.valueOf(1.75)));
    }

}
