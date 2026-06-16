package com.aulkhami.pakupos.modules.pos.interactors;

import com.aulkhami.pakupos.app.interactors.Interactor;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import com.aulkhami.pakupos.modules.inventory.dtos.ProductResponseDTO;
import com.aulkhami.pakupos.modules.pos.dtos.CartItemDTO;
import com.aulkhami.pakupos.modules.pos.dtos.OrderRequestDTO;
import com.aulkhami.pakupos.modules.pos.dtos.OrderResponseDTO;
import com.aulkhami.pakupos.modules.pos.models.POSModel;
import com.aulkhami.pakupos.modules.pos.services.POSService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class POSInteractor implements Interactor {
    private final POSModel model;
    private final POSService posService;

    public POSInteractor(POSModel model) {
        this.model = model;
        this.posService = new POSService();
    }

    public void loadCatalog() {
        try {
            List<ProductResponseDTO> products = posService.getCatalog();
            model.getCatalog().setAll(products);
        } catch (Exception e) {
            AlertHelper.showError("Error", "Could not load products: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addToCart(ProductResponseDTO product) {
        // Check if item already exists in cart
        Optional<CartItemDTO> existingItem = model.getCart().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItemDTO item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
            // Trigger an update by replacing the item
            int index = model.getCart().indexOf(item);
            model.getCart().set(index, item);
        } else {
            model.getCart().add(new CartItemDTO(product, 1));
        }
    }

    public void checkout() {
        String customerName = model.getCustomerName();
        if (customerName == null || customerName.trim().isEmpty()) {
            AlertHelper.showError("Validation Error", "Please enter customer name.");
            return;
        }

        if (model.getCart().isEmpty()) {
            AlertHelper.showError("Validation Error", "Cart is empty.");
            return;
        }

        try {
            List<CartItemDTO> cartItems = new ArrayList<>(model.getCart());
            OrderRequestDTO request = new OrderRequestDTO(customerName, cartItems, "cash"); // default payment for now
            OrderResponseDTO response = posService.processCheckout(request);

            AlertHelper.showSuccess(
                "POS",
                "Order placed successfully! Order Code: " + response.getOrderCode()
            );
            
            // Clear cart and customer name on success
            model.getCart().clear();
            model.setCustomerName("");
            
        } catch (IllegalStateException | IllegalArgumentException e) {
             AlertHelper.showError("Validation Error", e.getMessage());
        } catch (Exception e) {
            AlertHelper.showError("System Error", "Failed to process checkout: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
