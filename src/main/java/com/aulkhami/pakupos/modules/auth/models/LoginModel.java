/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aulkhami.pakupos.modules.auth.models;

import com.aulkhami.pakupos.models.Model;
import javafx.beans.property.*;

/**
 *
 * @author Rakha
 */
public class LoginModel implements Model {

    private StringProperty email = new SimpleStringProperty("");
    private StringProperty password = new SimpleStringProperty("");

    private BooleanProperty success = new SimpleBooleanProperty(false);

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public BooleanProperty successProperty() {
        return success;
    }

    private StringProperty errorText = new SimpleStringProperty("");

    public void setErrorText(String error) {
        this.errorText.set(error);
    }

    public String getErrorText() {
        return errorText.get();
    }

    public StringProperty errorTextProperty() {
        return errorText;
    }
}


