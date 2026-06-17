package com.aulkhami.pakupos.modules.dashboard.view;

import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.dashboard.models.TransactionItemModel;
import com.aulkhami.pakupos.views.View;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TransactionItemView implements View {

    @FXML
    private Label customerNameLabel;

    @FXML
    private Label detailsLabel;

    @FXML
    private Label amountLabel;

    @Override
    public void setModel(Model model) {
        TransactionItemModel itemModel = (TransactionItemModel) model;
        customerNameLabel.textProperty().bind(itemModel.customerNameProperty());
        detailsLabel.textProperty().bind(itemModel.detailsProperty());
        amountLabel.textProperty().bind(itemModel.amountProperty());
    }

    @Override
    public void setInteractor(Interactor interactor) {
        // Not used, but required by View interface
    }
}
