package com.INFM255.data;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Medicine {
    private Integer id;
    private String name;
    private String description;
    private Integer diseaseId;
    private BigDecimal price;
}
