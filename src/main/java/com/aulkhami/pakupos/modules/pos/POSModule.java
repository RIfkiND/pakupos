package com.aulkhami.pakupos.modules.pos;

import com.aulkhami.pakupos.modules.pos.controllers.POSController;

public class POSModule {
    
    private final POSController controller;

    public POSModule() {
        this.controller = new POSController();
    }

    public POSController getController() {
        return controller;
    }
}
