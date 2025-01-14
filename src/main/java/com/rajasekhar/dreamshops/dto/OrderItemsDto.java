package com.rajasekhar.dreamshops.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemsDto {
    private Long productId;
    private Integer quantity;
    private String productName;
    private BigDecimal price;
}
