package com.aulkhami.pakupos.modules.inventory;

import com.aulkhami.pakupos.modules.inventory.controllers.InventoryController;

public class InventoryModule {
    
    private final InventoryController controller;

    public InventoryModule() {
        this.controller = new InventoryController();
    }

    public InventoryController getController() {
        return controller;
    }
}
