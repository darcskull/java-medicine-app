package com.INFM255.data;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {
    private Integer id;
    private Integer medicineId;
    private Integer number;
    private BigDecimal price;
    private String address;
    private String userId;
    private String phoneNumber;
}
