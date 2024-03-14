package com.INFM255.data;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MedicineView {
    private String name;
    private String description;
    private String disease;
    private BigDecimal price;
}
