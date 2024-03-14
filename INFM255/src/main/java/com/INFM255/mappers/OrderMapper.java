package com.INFM255.mappers;

import com.INFM255.data.Order;
import com.INFM255.data.OrderView;

public class OrderMapper {

    public static OrderView maptoOrderView(Order order, String userEmail, String medicine){
        return OrderView.builder()
                .id(order.getId())
                .price(order.getPrice())
                .address(order.getAddress())
                .number(order.getNumber())
                .phoneNumber(order.getPhoneNumber())
                .userEmail(userEmail)
                .medicine(medicine)
                .build();

    }
}
