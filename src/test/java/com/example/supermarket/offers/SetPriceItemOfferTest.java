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
import com.example.supermarket.offers.SetPriceItemOffer;

public class SetPriceItemOfferTest {

    private SetPriceItemOffer offer;
    private Items items;
    private Basket basket;

    @Before
    public void setUp() {
        items = new Items();
        Item coke = new Item("Coke", BigDecimal.valueOf(0.8));
        Item banana = new Item("Banana", BigDecimal.valueOf(0.25));
        items.putItem(coke);
        items.putItem(banana);

        offer = new SetPriceItemOffer(items, "Coke", 2, BigDecimal.ONE);

        basket = new Basket();
    }

    @Test
    public void whenItemIsNotInTheBasket_ThenThereAreNoDiscounts() {
        assertEquals(emptyList(), offer.apply(basket));
    }

    @Test
    public void whenThereAreNotEnoughItemsInTheBasket_ThenThereAreNoDiscounts() {
        basket.addItem("Coke");
        assertEquals(emptyList(), offer.apply(basket));
    }

    @Test
    public void whenAmountOfItemsIsEqualToOfferQuantity_ThenThereIsOneDiscount() {
        basket.addItem("Coke", 2);

        List<Discount> discounts = offer.apply(basket);
        assertEquals(1, discounts.size());
        Discount discount = discounts.get(0);

        assertEquals(offer, discount.getOffer());
        assertEquals(0, discount.getAmount()
                                .compareTo(BigDecimal.valueOf(0.6)));
    }

    @Test
    public void whenAmountOfItemsIsAMultipleOfOfferQuantity_ThenThereAreMultipleDiscounts() {
        basket.addItem("Coke", 4);

        List<Discount> discounts = offer.apply(basket);
        assertEquals(2, discounts.size());
        for (Discount discount : discounts) {
            assertEquals(offer, discount.getOffer());
            assertEquals(0, discount.getAmount()
                                    .compareTo(BigDecimal.valueOf(0.6)));
        }
    }

    @Test
    public void whenAmountOfItemsIsNotAMultipleOfOfferQuantity_ThenTheCorrectNumberOfDiscountsAreApplied() {
        basket.addItem("Coke", 3);

        List<Discount> discounts = offer.apply(basket);
        assertEquals(1, discounts.size());
        Discount discount = discounts.get(0);

        assertEquals(offer, discount.getOffer());
        assertEquals(0, discount.getAmount()
                                .compareTo(BigDecimal.valueOf(0.6)));
    }

}
