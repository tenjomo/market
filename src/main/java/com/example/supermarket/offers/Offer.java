package com.example.supermarket.offers;

import java.util.List;
import com.example.supermarket.Basket;
import com.example.supermarket.Discount;
import com.example.supermarket.Items;

public abstract class Offer {

    private final Items items;

    protected Offer(final Items items) {
        this.items = items;
    }

    protected Items getItems() {
        return items;
    }

    public abstract List<Discount> apply(Basket basket);

}
