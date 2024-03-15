package com.INFM255.service;

import com.INFM255.data.Medicine;
import com.INFM255.data.Order;
import com.INFM255.data.OrderView;
import com.INFM255.data.User;
import com.INFM255.exception.BadRequestException;
import com.INFM255.exception.GeneralException;
import com.INFM255.repository.MedicineRepository;
import com.INFM255.repository.OrderRepository;
import com.INFM255.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testFindOrdersForUser() {
        Integer userId = 1;
        String userEmail = "test@example.com";
        String medicineName = "Aspirin";

        Order order = new Order();
        order.setUserId(userId.toString());
        order.setMedicineId(1);
        order.setAddress("123 Main St");

        List<Order> orders = new ArrayList<>();
        orders.add(order);

        when(orderRepository.findOrdersByUserId(userId.toString())).thenReturn(orders);
        User user = new User();
        user.setEmail(userEmail);
        when(userRepository.findUserById(userId)).thenReturn(user);
        Medicine medicine = new Medicine();
        medicine.setName(medicineName);
        when(medicineRepository.findMedicineById(order.getMedicineId())).thenReturn(medicine);

        List<OrderView> result = orderService.findOrdersForUser(userId);

        assertEquals(1, result.size());
        assertEquals(userEmail, result.get(0).getUserEmail());
        assertEquals(medicineName, result.get(0).getMedicine());
    }

    @Test
    void testCreateOrder_WithValidData() {
        Order order = new Order();
        order.setAddress("123 Main St");

        Medicine medicine = new Medicine();
        medicine.setId(1);

        String medicineName = "Aspirin";

        when(medicineRepository.findMedicineByName(medicineName)).thenReturn(medicine);

        assertDoesNotThrow(() -> orderService.createOrder(order, medicineName));

        verify(orderRepository, times(1)).createOrder(order);
    }

    @Test
    void testCreateOrder_WithEmptyAddress() {
        Order order = new Order();
        order.setAddress("");

        String medicineName = "Aspirin";

        assertThrows(BadRequestException.class, () -> orderService.createOrder(order, medicineName));
    }

    @Test
    void testCreateOrder_WithException() {
        Order order = new Order();
        order.setAddress("123 Main St");

        Medicine medicine = new Medicine();
        medicine.setId(1);

        String medicineName = "Aspirin";

        when(medicineRepository.findMedicineByName(medicineName)).thenReturn(medicine);
        doThrow(new RuntimeException()).when(orderRepository).createOrder(order);

        assertThrows(GeneralException.class, () -> orderService.createOrder(order, medicineName));
    }
}
