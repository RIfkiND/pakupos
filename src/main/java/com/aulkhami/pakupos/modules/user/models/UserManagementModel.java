package com.aulkhami.pakupos.modules.user.models;

import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.user.dtos.UserResponseDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserManagementModel implements Model {
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty email = new SimpleStringProperty("");
    private final StringProperty password = new SimpleStringProperty("");
    private final StringProperty phone = new SimpleStringProperty("");
    private final ObservableList<UserResponseDTO> users = FXCollections.observableArrayList();

    public StringProperty nameProperty() { return name; }
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }

    public StringProperty emailProperty() { return email; }
    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }

    public StringProperty passwordProperty() { return password; }
    public String getPassword() { return password.get(); }
    public void setPassword(String password) { this.password.set(password); }

    public StringProperty phoneProperty() { return phone; }
    public String getPhone() { return phone.get(); }
    public void setPhone(String phone) { this.phone.set(phone); }

    public ObservableList<UserResponseDTO> getUsers() { return users; }
}

