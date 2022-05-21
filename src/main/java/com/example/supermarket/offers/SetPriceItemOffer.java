package com.example.supermarket.offers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.example.supermarket.Basket;
import com.example.supermarket.Discount;
import com.example.supermarket.Items;


public class SetPriceItemOffer extends Offer {

    private final String     itemCode;
    private final int        quantity;
    private final BigDecimal price;


    public SetPriceItemOffer(final Items items, final String itemCode, final int quantity, final BigDecimal price) {
        super(items);
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public List<Discount> apply(final Basket basket) {
        if (!basket.getItems()
                   .containsKey(itemCode)) {
            return new ArrayList<>();
        }

        int itemCount = basket.getItems()
                              .get(itemCode);
        int discountCount = itemCount / quantity;
        if (discountCount == 0) {
            return new ArrayList<>();
        }

        List<Discount> discounts = new ArrayList<>();
        BigDecimal priceBeforeDiscount = getItems().getItem(itemCode)
                                                   .getPrice()
                                                   .multiply(BigDecimal.valueOf(quantity));
        BigDecimal discountAmount = priceBeforeDiscount.subtract(price);
        for (int i = 0; i < discountCount; i++) {
            discounts.add(new Discount(this, discountAmount));
        }
        return discounts;
    }

}
