package com.INFM255.controller;

import com.INFM255.data.OrderView;
import com.INFM255.data.User;
import com.INFM255.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public String findOrdersForUser(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        List<OrderView> availableOrders = orderService.findOrdersForUser(user.getId());
        model.addAttribute("orderList", availableOrders);
        return "patients/orders";
    }

}
