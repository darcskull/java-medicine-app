package com.INFM255.service;

import com.INFM255.data.Order;
import com.INFM255.exception.GeneralException;
import com.INFM255.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> findOrdersForUser(Integer userId) {
        return orderRepository.findOrdersByUserId(userId.toString());
    }

    public void deleteOrder(String orderId) {
        orderRepository.deleteOrderById(Integer.getInteger(orderId));
    }

    public void createOrder(Order order) {
        try {
            orderRepository.createOrder(order);
        } catch (Exception exception) {
            throw new GeneralException("An error has occurred, the provided data was not registered");
        }
    }
}
