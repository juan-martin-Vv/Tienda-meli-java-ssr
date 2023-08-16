package com.jmvv.MpShop.models;
import java.math.BigDecimal;

public record phoneOrder(
    String img,
    String title,
    BigDecimal price,
    Integer unit,
    String id,
    String preferenceId

) {
    
}
