package com.aulkhami.pakupos.modules.dashboard.interactors;

import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.modules.auth.services.AuthService;

public class DashboardInteractor implements Interactor {
    private final AuthService authService;

    public DashboardInteractor() {
        this.authService = new AuthService();
    }

    public void logout() {
        authService.logout();
    }
}
