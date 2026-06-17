package com.aulkhami.pakupos.modules.dashboard.interactors;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import com.aulkhami.pakupos.interactors.MenuBarInteractor;
import com.aulkhami.pakupos.modules.auth.services.AuthService;
import com.aulkhami.pakupos.modules.dashboard.models.DashboardModel;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import com.aulkhami.pakupos.modules.pos.repositories.OrderRepository;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardInteractor extends MenuBarInteractor {

    private final DashboardModel model;
    private final AuthService authService;
    private final OrderRepository orderRepository;

    public DashboardInteractor(DashboardModel model) {
        this.model = model;
        this.authService = new AuthService();
        this.orderRepository = new OrderRepository();
    }

    public void loadDashboardData() {
        try {
            java.math.BigDecimal todaySales = orderRepository.getTotalSalesToday();
            model.setSalesAmount(todaySales != null ? todaySales.longValue() : 0L);
            model.setSalesCount(orderRepository.getOrderCountToday());

            List<Order> orders = orderRepository.findAll();
            List<Order> recent = orders.stream().limit(5).collect(Collectors.toList());
            model.getRecentTransactions().setAll(recent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void navReports() {
        try {
            App.navigate("report");
        } catch (IOException e) {
            AlertHelper.showError(
                    "System Error",
                    "Could not load Reports screen."
            );
            e.printStackTrace();
        }
    }

    public void logout() {
        authService.logout();
    }
}
