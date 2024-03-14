package com.INFM255.data;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderView {
    private Integer id;
    private String medicine;
    private Integer number;
    private BigDecimal price;
    private String address;
    private String userEmail;
    private String phoneNumber;
}
