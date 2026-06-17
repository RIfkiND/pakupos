package com.aulkhami.pakupos.modules.report.interactors;

import com.aulkhami.pakupos.modules.pos.repositories.OrderRepository;
import com.aulkhami.pakupos.app.interactors.Interactor;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import com.aulkhami.pakupos.modules.report.models.ReportModel;
import java.math.BigDecimal;
import java.util.List;

public class ReportInteractor implements Interactor {
    private final ReportModel model;
    private final OrderRepository orderRepository = new OrderRepository();

    public ReportInteractor(ReportModel model) {
        this.model = model;
    }

    public void loadReportData() {
        BigDecimal totalSales = orderRepository.getTotalSalesToday();
        int totalOrders = orderRepository.getOrderCountToday();
        List<Order> orders = orderRepository.findAll();

        model.setTotalSales(totalSales != null ? totalSales : BigDecimal.ZERO);
        model.setTotalOrders(totalOrders);
        model.getTransactions().setAll(orders);
    }
}

