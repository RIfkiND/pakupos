package com.aulkhami.pakupos.modules.dashboard.interactors;

import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.modules.dashboard.models.TransactionItemModel;

public class TransactionItemInteractor implements Interactor {

    private final TransactionItemModel model;

    public TransactionItemInteractor(TransactionItemModel model) {
        this.model = model;
    }
}
