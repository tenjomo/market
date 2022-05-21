package com.example.supermarket;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.example.supermarket.offers.Offer;

@RunWith(MockitoJUnitRunner.class)
public class BasketPricerTest {

    private BasketPricer basketPricer;
    private Basket       basket;
    private List<Offer>  offers;

    @Mock
    private Offer        offer1, offer2;

    @Before
    public void setUp() {
        Items items = new Items();
        items.putItem(new Item("Bran Flakes", BigDecimal.valueOf(2)));
        items.putItem(new Item("Orange Juice", BigDecimal.valueOf(1.29)));
        items.putItem(new Item("Onions", BigDecimal.valueOf(0.7)));
        items.putItem(new Item("Potatoes", BigDecimal.valueOf(0.8)));
        items.putItem(new Item("Oranges", BigDecimal.valueOf(1.99)));

        offers = new ArrayList<>();

        basketPricer = new BasketPricer(items, offers);

        basket = new Basket();
        basket.addItem("Bran Flakes", 2);
        basket.addItem("Orange Juice");
        basket.addWeightedItem("Onions", BigDecimal.valueOf(0.6));
        basket.addWeightedItem("Potatoes", BigDecimal.valueOf(1.5));
    }

    @Test
    public void itemsAreSummedCorrectly() {
        Bill bill = basketPricer.price(basket);

        assertEquals(0, bill.getPrice()
                            .compareTo(BigDecimal.valueOf(6.91)));

        assertEquals(0, bill.getPriceBeforeDiscounts()
                            .compareTo(BigDecimal.valueOf(6.91)));

        assertEquals(0, bill.getTotalDiscount()
                            .compareTo(BigDecimal.ZERO));

        assertEquals(emptyList(), bill.getDiscounts());
        assertEquals(basket, bill.getBasket());
    }

    @Test
    public void discountsAreAppliedCorrectly() {
        offers.add(offer1);
        List<Discount> discounts = new ArrayList<>();
        discounts.add(new Discount(offer1, BigDecimal.valueOf(0.7)));
        when(offer1.apply(basket)).thenReturn(discounts);

        Bill bill = basketPricer.price(basket);

        assertEquals(0, bill.getPrice()
                            .compareTo(BigDecimal.valueOf(6.21)));

        assertEquals(0, bill.getPriceBeforeDiscounts()
                            .compareTo(BigDecimal.valueOf(6.91)));

        assertEquals(0, bill.getTotalDiscount()
                            .compareTo(BigDecimal.valueOf(0.7)));

        assertEquals(discounts, bill.getDiscounts());
        assertEquals(basket, bill.getBasket());
    }

    @Test
    public void multipleDiscountsAreAppliedCorrectly() {
        offers.add(offer1);
        List<Discount> offer1Discounts = new ArrayList<>();
        offer1Discounts.add(new Discount(offer1, BigDecimal.valueOf(0.4)));
        when(offer1.apply(basket)).thenReturn(offer1Discounts);

        offers.add(offer2);
        List<Discount> offer2Discounts = new ArrayList<>();
        offer2Discounts.add(new Discount(offer2, BigDecimal.valueOf(1.25)));
        offer2Discounts.add(new Discount(offer2, BigDecimal.valueOf(1.25)));
        when(offer2.apply(basket)).thenReturn(offer2Discounts);

        Bill bill = basketPricer.price(basket);

        assertEquals(0, bill.getPrice()
                            .compareTo(BigDecimal.valueOf(4.01)));

        assertEquals(0, bill.getPriceBeforeDiscounts()
                            .compareTo(BigDecimal.valueOf(6.91)));

        assertEquals(0, bill.getTotalDiscount()
                            .compareTo(BigDecimal.valueOf(2.9)));

        List<Discount> allDiscounts = new ArrayList<>();
        allDiscounts.addAll(offer1Discounts);
        allDiscounts.addAll(offer2Discounts);
        assertEquals(allDiscounts, bill.getDiscounts());

        assertEquals(basket, bill.getBasket());
    }

    @Test
    public void pricesAreRoundedUpWhenNecessary() {
        basket = new Basket();
        basket.addWeightedItem("Oranges", BigDecimal.valueOf(0.2));

        // 0.398 -> 0.4
        assertEquals(0, basketPricer.sumWeightedItems(basket)
                                    .compareTo(BigDecimal.valueOf(0.4)));

        basket = new Basket();
        basket.addWeightedItem("Oranges", BigDecimal.valueOf(0.5));

        // 0.995 -> 1
        assertEquals(0, basketPricer.sumWeightedItems(basket)
                                    .compareTo(BigDecimal.ONE));
    }

    @Test
    public void pricesAreRoundedDownWhenNecessary() {
        basket = new Basket();
        basket.addWeightedItem("Oranges", BigDecimal.valueOf(0.6));

        // 1.194 -> 1.19
        assertEquals(0, basketPricer.sumWeightedItems(basket)
                                    .compareTo(BigDecimal.valueOf(1.19)));
    }

}
