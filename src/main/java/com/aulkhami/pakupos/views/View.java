/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.aulkhami.pakupos.views;

import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.models.Model;

/**
 *
 * @author Rakha
 */
public interface View {

    public void setModel(Model model);

    public void setInteractor(Interactor interactor);
}
