package com.aulkhami.pakupos.modules.dashboard.controllers;

import com.aulkhami.pakupos.controllers.Controller;
import com.aulkhami.pakupos.modules.dashboard.interactors.TransactionItemInteractor;
import com.aulkhami.pakupos.modules.dashboard.models.TransactionItemModel;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

public class TransactionItemController implements Controller {

    private final TransactionItemModel model;
    private final TransactionItemInteractor interactor;

    public TransactionItemController(Order order) {
        this.model = new TransactionItemModel(order);
        this.interactor = new TransactionItemInteractor(model);
    }

    @Override
    public Region getView() throws IOException {
        return Controller.loadView(
            new FXMLLoader(getClass().getResource("/com/aulkhami/pakupos/app/dashboard/transaction-item.fxml")),
            model,
            interactor
        );
    }
}
