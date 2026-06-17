/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aulkhami.pakupos.interactors;

import com.aulkhami.pakupos.models.Model;

/**
 *
 * @author Rakha
 */
public abstract class FormInteractor implements Interactor {

    private Model model;

    public FormInteractor(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public boolean canSubmit() {
        return true;
    }

    public abstract void submitForm();
}
