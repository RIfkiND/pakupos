package com.aulkhami.pakupos.modules.dashboard.services;

import com.aulkhami.pakupos.modules.pos.repositories.OrderRepository;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import com.aulkhami.pakupos.modules.pos.dtos.OrderResponseDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardService {
    private final OrderRepository orderRepository;

    public DashboardService() {
        this.orderRepository = new OrderRepository();
    }

    public BigDecimal getTotalSalesToday() {
        return orderRepository.getTotalSalesToday();
    }

    public int getOrderCountToday() {
        return orderRepository.getOrderCountToday();
    }

    public List<OrderResponseDTO> getRecentTransactions(int limit) {
        List<Order> allOrders = orderRepository.findAll();
        return allOrders.stream()
                .limit(limit)
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
    }
}
