package com.jmvv.MpShop.models;

public record productosMp(
    Integer Id,
    String productName,
    String productDescription,
    String productImg,
    Integer units,
    Double price
) {
    
}
