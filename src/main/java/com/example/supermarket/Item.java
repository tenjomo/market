package com.example.supermarket;

import java.math.BigDecimal;


public class Item {

    private final String     code;
    private final BigDecimal price;

    public Item(final String code, final BigDecimal price) {
        this.code = code;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
