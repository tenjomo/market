package com.example.supermarket;

import java.math.BigDecimal;
import java.util.List;


public class Bill {

    private final Basket         basket;
    private final BigDecimal     price;
    private final BigDecimal     priceBeforeDiscounts;
    private final List<Discount> discounts;
    private final BigDecimal     totalDiscount;


    public Bill(final Basket basket, final BigDecimal price, final BigDecimal priceBeforeDiscounts,
            final List<Discount> discounts, final BigDecimal totalDiscount) {
        this.basket = basket;
        this.price = price;
        this.priceBeforeDiscounts = priceBeforeDiscounts;
        this.discounts = discounts;
        this.totalDiscount = totalDiscount;
    }

    public Basket getBasket() {
        return basket;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getPriceBeforeDiscounts() {
        return priceBeforeDiscounts;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

}
