package com.INFM255.repository;

import com.INFM255.data.Order;
import com.INFM255.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Order> findOrdersByUserId(String userId) {
        String query = "SELECT * FROM \"Order\" WHERE userId = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, userId);
        List<Order> orders = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            Order order = new Order();
            order.setId((Integer) row.get("id"));
            order.setMedicineId((Integer) row.get("medicineId"));
            order.setNumber((Integer) row.get("number"));
            order.setPrice((BigDecimal) row.get("price"));
            order.setAddress((String) row.get("address"));
            order.setUserId((String) row.get("userId"));
            order.setPhoneNumber((String) row.get("phoneNumber"));
            orders.add(order);
        }

        return orders;
    }

    public void deleteOrderById(Integer orderId) {
        String query = "DELETE FROM \"Order\" WHERE id = ?";
        jdbcTemplate.update(query, orderId);
    }

    public void createOrder(Order order) {
        String query = "INSERT INTO \"Order\" (id,medicineId, number, price, address, userId, phoneNumber) " +
                "VALUES (nextval('order_sequence'),?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query,
                order.getMedicineId(),
                order.getNumber(),
                order.getPrice(),
                order.getAddress(),
                order.getUserId(),
                order.getPhoneNumber());
    }
}
