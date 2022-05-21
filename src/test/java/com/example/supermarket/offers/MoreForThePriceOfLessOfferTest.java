package com.example.supermarket.offers;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.example.supermarket.Basket;
import com.example.supermarket.Discount;
import com.example.supermarket.Item;
import com.example.supermarket.Items;
import com.example.supermarket.offers.MoreForThePriceOfLessOffer;

public class MoreForThePriceOfLessOfferTest {

    private MoreForThePriceOfLessOffer offer;
    private Items items;
    private Basket basket;

    @Before
    public void setUp() {
        items = new Items();
        Item beans = new Item("Beans", BigDecimal.valueOf(0.5));
        Item oranges = new Item("Oranges", BigDecimal.valueOf(1.99));
        items.putItem(beans);
        items.putItem(oranges);

        offer = new MoreForThePriceOfLessOffer(items, "Beans", 3, 2);

        basket = new Basket();
    }

    @Test
    public void whenItemIsNotInTheBasket_ThenThereAreNoDiscounts() {
        assertEquals(emptyList(), offer.apply(basket));
    }

    @Test
    public void whenThereAreNotEnoughItemsInTheBasket_ThenThereAreNoDiscounts() {
        basket.addItem("Beans", 2);
        assertEquals(emptyList(), offer.apply(basket));
    }

    @Test
    public void whenAmountOfItemsIsEqualToMoreQuantity_ThenThereIsOneDiscount() {
        basket.addItem("Beans", 3);

        List<Discount> discounts = offer.apply(basket);
        assertEquals(1, discounts.size());
        Discount discount = discounts.get(0);

        assertEquals(offer, discount.getOffer());
        assertEquals(0, discount.getAmount()
                                .compareTo(BigDecimal.valueOf(0.5)));
    }

    @Test
    public void whenAmountOfItemsIsAMultipleOfMoreQuantity_ThenThereAreMultipleDiscounts() {
        basket.addItem("Beans", 6);

        List<Discount> discounts = offer.apply(basket);
        assertEquals(2, discounts.size());
        for (Discount discount : discounts) {
            assertEquals(offer, discount.getOffer());
            assertEquals(0, discount.getAmount()
                                    .compareTo(BigDecimal.valueOf(0.5)));
        }
    }

    @Test
    public void whenAmountOfItemsIsNotAMultipleOfMoreQuantity_ThenTheCorrectNumberOfDiscountsAreApplied() {
        basket.addItem("Beans", 5);

        List<Discount> discounts = offer.apply(basket);
        assertEquals(1, discounts.size());
        Discount discount = discounts.get(0);

        assertEquals(offer, discount.getOffer());
        assertEquals(0, discount.getAmount()
                                .compareTo(BigDecimal.valueOf(0.5)));
    }

}
