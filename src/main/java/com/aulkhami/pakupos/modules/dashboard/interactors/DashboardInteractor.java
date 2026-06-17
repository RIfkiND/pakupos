package com.aulkhami.pakupos.modules.dashboard.interactors;

import com.aulkhami.pakupos.app.interactors.Interactor;
import com.aulkhami.pakupos.modules.auth.services.AuthService;
import com.aulkhami.pakupos.modules.dashboard.services.DashboardService;
import com.aulkhami.pakupos.modules.pos.dtos.OrderResponseDTO;
import com.aulkhami.pakupos.modules.dashboard.models.DashboardModel;
import java.util.List;

public class DashboardInteractor implements Interactor {
    private final AuthService authService;
    private final DashboardService dashboardService;
    private final DashboardModel model;

    public DashboardInteractor(DashboardModel model) {
        this.model = model;
        this.authService = new AuthService();
        this.dashboardService = new DashboardService();
    }

    public void loadDashboardData() {
        try {
            model.setTotalSales(dashboardService.getTotalSalesToday());
            model.setTotalOrders(dashboardService.getOrderCountToday());
            
            List<OrderResponseDTO> recent = dashboardService.getRecentTransactions(5);
            model.getRecentTransactions().setAll(recent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        authService.logout();
    }
}
