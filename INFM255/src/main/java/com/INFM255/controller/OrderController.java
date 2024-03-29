package com.INFM255.controller;

import com.INFM255.data.*;
import com.INFM255.service.DiagnosisService;
import com.INFM255.service.MedicineService;
import com.INFM255.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MedicineService medicineService;
    private final DiagnosisService diagnosisService;

    @GetMapping("/orders")
    public String findOrdersForUser(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        List<OrderView> availableOrders = orderService.findOrdersForUser(user.getId());
        model.addAttribute("orderList", availableOrders);
        return "patients/orders";
    }

    @PostMapping("/create/order")
    public ResponseEntity<Void> createOrder(@RequestBody Map<String, String> request, HttpSession session) {
        Order order = new Order();
        String medicine = request.get("medicineName");
        order.setAddress(request.get("address"));
        order.setPrice(new BigDecimal(request.get("price")));
        order.setNumber(Integer.valueOf(request.get("number")));
        User user = (User) session.getAttribute("loggedInUser");
        order.setPhoneNumber(user.getPhoneNumber());
        order.setUserId(user.getId().toString());
        orderService.createOrder(order, medicine);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/order/form")
    public String getOrderForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        List<Disease> diseases = diagnosisService.findPersonalDiseases(user.getId());
        List<MedicineView> views = medicineService.getPersonalMedicines(diseases);
        model.addAttribute("medicines", views);
        return "patients/createOrder";
    }

}
