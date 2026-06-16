package com.aulkhami.pakupos.modules.settings;

import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.user.entities.User;
import javafx.beans.property.SimpleObjectProperty;

public class SettingsModel implements Model {
    private final SimpleObjectProperty<User> currentUser = new SimpleObjectProperty<>();

    public SimpleObjectProperty<User> currentUserProperty() {
        return currentUser;
    }

    public User getCurrentUser() {
        return currentUser.get();
    }

    public void setCurrentUser(User user) {
        this.currentUser.set(user);
    }
}


