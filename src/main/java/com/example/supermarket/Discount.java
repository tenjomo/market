package com.example.supermarket;

import java.math.BigDecimal;
import com.example.supermarket.offers.Offer;


public class Discount {

    private final Offer      offer;
    private final BigDecimal amount;

    public Discount(final Offer offer, final BigDecimal amount) {
        this.offer = offer;
        this.amount = amount;
    }

    public Offer getOffer() {
        return offer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
