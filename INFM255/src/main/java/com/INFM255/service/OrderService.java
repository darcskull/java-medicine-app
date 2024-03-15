package com.INFM255.service;

import com.INFM255.data.Medicine;
import com.INFM255.data.Order;
import com.INFM255.data.OrderView;
import com.INFM255.exception.BadRequestException;
import com.INFM255.exception.GeneralException;
import com.INFM255.repository.MedicineRepository;
import com.INFM255.repository.OrderRepository;
import com.INFM255.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.INFM255.mappers.ViewMapper.mapToOrderView;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MedicineRepository medicineRepository;

    public List<OrderView> findOrdersForUser(Integer userId) {
        List<Order> orders = orderRepository.findOrdersByUserId(userId.toString());
        List<OrderView> views = new ArrayList<>();
        for (Order order : orders) {
            OrderView view = mapToOrderView(order,
                    userRepository.findUserById(Integer.parseInt(order.getUserId())).getEmail(),
                    medicineRepository.findMedicineById(order.getMedicineId()).getName());
            views.add(view);
        }

        return views;
    }

    public void createOrder(Order order, String medicineName) {
        if (Objects.equals(order.getAddress(), "")) {
            throw new BadRequestException("Empty values are not allowed");
        }
        Medicine medicine = medicineRepository.findMedicineByName(medicineName);
        order.setMedicineId(medicine.getId());
        try {
            orderRepository.createOrder(order);
        } catch (Exception exception) {
            throw new GeneralException("An error has occurred, the provided data was not registered");
        }
    }
}
