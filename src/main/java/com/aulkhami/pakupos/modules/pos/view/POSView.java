package com.aulkhami.pakupos.modules.pos.view;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.inventory.dtos.ProductResponseDTO;
import com.aulkhami.pakupos.modules.pos.dtos.CartItemDTO;
import com.aulkhami.pakupos.modules.pos.interactors.POSInteractor;
import com.aulkhami.pakupos.modules.pos.models.POSModel;
import com.aulkhami.pakupos.views.View;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class POSView implements View {

    private POSModel model;
    private POSInteractor interactor;

    @FXML
    private TabPane posTabPane;
    @FXML
    private TextField customerNameField;
    @FXML
    private VBox productListBox;
    @FXML
    private TextField searchField;
    @FXML
    private Label itemCountLabel;
    @FXML
    private Label totalPriceLabel;

    @FXML
    private VBox receiptItemsBox;
    @FXML
    private Label subtotalLabel;
    @FXML
    private Label taxLabel;
    @FXML
    private Label grandTotalLabel;

    @FXML
    private Label cashGrandTotalLabel;
    @FXML
    private TextField cashInputField;
    @FXML
    private Label changeLabel;

    @FXML
    private Label qrisGrandTotalLabel;

    private BigDecimal currentGrandTotal = BigDecimal.ZERO;

    @Override
    public void setModel(Model model) {
        this.model = (POSModel) model;
        if (customerNameField != null) {
            customerNameField.textProperty().bindBidirectional(this.model.customerNameProperty());
        }

        this.model.getCart().addListener((ListChangeListener<CartItemDTO>) change -> {
            updateCartSummary();
            renderProducts();
            updateReceipt();
        });

        this.model.getCatalog().addListener((ListChangeListener<ProductResponseDTO>) change -> renderProducts());

        renderProducts();
        updateCartSummary();
        updateReceipt();

        if (cashInputField != null) {
            cashInputField.textProperty().addListener((obs, oldV, newV) -> calculateChange());
        }
    }

    @Override
    public void setInteractor(Interactor interactor) {
        this.interactor = (POSInteractor) interactor;
        this.interactor.loadCatalog();

        if (searchField != null) {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                this.interactor.searchCatalog(newValue);
            });
        }
    }

    private void renderProducts() {
        if (productListBox == null) {
            return;
        }
        productListBox.getChildren().clear();
        for (ProductResponseDTO p : model.getCatalog()) {
            HBox card = new HBox(12);
            card.setAlignment(Pos.CENTER_LEFT);
            card.getStyleClass().add("product-list-card");
            card.setPadding(new Insets(14, 16, 14, 16));

            VBox infoBox = new VBox(4);
            HBox.setHgrow(infoBox, Priority.ALWAYS);

            Label nameLabel = new Label(p.getName());
            nameLabel.getStyleClass().add("product-name");

            HBox priceBox = new HBox(8);
            priceBox.setAlignment(Pos.CENTER_LEFT);
            Label priceLabel = new Label(com.aulkhami.pakupos.app.utils.CurrencyHelper.formatRupiah(p.getPrice()));
            priceLabel.getStyleClass().add("product-price");

            String cat = p.getCategory() != null ? p.getCategory() : "Umum";
            Label badgeLabel = new Label(cat);
            badgeLabel.getStyleClass().add("badge");

            String catLower = cat.toLowerCase();
            if (catLower.contains("manis")) {
                card.getStyleClass().add("card-sweet");
                badgeLabel.getStyleClass().add("badge-sweet");
            } else if (catLower.contains("asin")) {
                card.getStyleClass().add("card-savory");
                badgeLabel.getStyleClass().add("badge-savory");
            } else if (catLower.contains("minuman")) {
                card.getStyleClass().add("card-drink");
                badgeLabel.getStyleClass().add("badge-drink");
            } else {
                card.getStyleClass().add("card-general");
                badgeLabel.getStyleClass().add("badge-general");
            }

            priceBox.getChildren().addAll(priceLabel, badgeLabel);
            infoBox.getChildren().addAll(nameLabel, priceBox);

            Label separator = new Label("—");
            separator.getStyleClass().add("product-separator");

            HBox qtyBox = new HBox(8);
            qtyBox.setAlignment(Pos.CENTER);
            qtyBox.getStyleClass().add("quantity-control-container");

            Button minusBtn = new Button("-");
            minusBtn.getStyleClass().addAll("quantity-btn", "btn-minus");
            minusBtn.setFocusTraversable(false);
            minusBtn.setOnAction(e -> interactor.decreaseQuantity(p));

            int qty = getCartQuantity(p);
            Label qtyLabel = new Label(String.valueOf(qty));
            qtyLabel.getStyleClass().add("quantity-text");

            Button plusBtn = new Button("+");
            plusBtn.getStyleClass().addAll("quantity-btn", "btn-plus");
            plusBtn.setFocusTraversable(false);
            plusBtn.setOnAction(e -> interactor.addToCart(p));

            qtyBox.getChildren().addAll(plusBtn, qtyLabel, minusBtn);

            card.getChildren().addAll(infoBox, separator, qtyBox);
            productListBox.getChildren().add(card);
        }
    }

    private int getCartQuantity(ProductResponseDTO product) {
        Optional<CartItemDTO> item = model.getCart().stream()
                .filter(c -> c.getProduct().getId().equals(product.getId()))
                .findFirst();
        return item.map(CartItemDTO::getQuantity).orElse(0);
    }

    private void updateCartSummary() {
        if (itemCountLabel == null || totalPriceLabel == null) {
            return;
        }
        int count = model.getCart().stream().mapToInt(CartItemDTO::getQuantity).sum();
        itemCountLabel.setText(count + " Items");
        BigDecimal subtotal = getSubtotal();
        totalPriceLabel.setText(com.aulkhami.pakupos.app.utils.CurrencyHelper.formatRupiah(subtotal));
    }

    private void updateReceipt() {
        if (receiptItemsBox == null) {
            return;
        }
        receiptItemsBox.getChildren().clear();
        for (CartItemDTO item : model.getCart()) {
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER_LEFT);
            row.getStyleClass().add("receipt-item-row");

            Label nameQty = new Label(item.getProduct().getName() + " (Qty: " + item.getQuantity() + ")");
            nameQty.getStyleClass().add("receipt-item-name");
            HBox.setHgrow(nameQty, Priority.ALWAYS);
            nameQty.setMaxWidth(Double.MAX_VALUE);

            Label subtotal = new Label(com.aulkhami.pakupos.app.utils.CurrencyHelper.formatRupiah(item.getSubtotal()));
            subtotal.getStyleClass().add("receipt-item-price");

            row.getChildren().addAll(nameQty, subtotal);
            receiptItemsBox.getChildren().add(row);
        }

        BigDecimal subtotal = getSubtotal();
        BigDecimal tax = subtotal.multiply(new BigDecimal("0.11"));
        currentGrandTotal = subtotal.add(tax);

        if (subtotalLabel != null) {
            subtotalLabel.setText(com.aulkhami.pakupos.app.utils.CurrencyHelper.formatRupiah(subtotal));
        }
        if (taxLabel != null) {
            taxLabel.setText(com.aulkhami.pakupos.app.utils.CurrencyHelper.formatRupiah(tax));
        }
        if (grandTotalLabel != null) {
            grandTotalLabel.setText(com.aulkhami.pakupos.app.utils.CurrencyHelper.formatRupiah(currentGrandTotal));
        }
        if (cashGrandTotalLabel != null) {
            cashGrandTotalLabel.setText(com.aulkhami.pakupos.app.utils.CurrencyHelper.formatRupiah(currentGrandTotal));
        }
        if (qrisGrandTotalLabel != null) {
            qrisGrandTotalLabel.setText(com.aulkhami.pakupos.app.utils.CurrencyHelper.formatRupiah(currentGrandTotal));
        }
    }

    private BigDecimal getSubtotal() {
        return model.getCart().stream()
                .map(CartItemDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @FXML
    private void goToStep2() {
        if (model.getCart().isEmpty()) {
            return;
        }
        posTabPane.getSelectionModel().select(1);
    }

    @FXML
    private void goToStep3() {
        posTabPane.getSelectionModel().select(2);
    }

    @FXML
    private void selectCashPayment() {
        posTabPane.getSelectionModel().select(3);
        cashInputField.setText("");
        changeLabel.setText("Rp 0");
    }

    @FXML
    private void selectQrisPayment() {
        posTabPane.getSelectionModel().select(4);
    }

    @FXML
    private void goBackToPayment() {
        posTabPane.getSelectionModel().select(2);
    }

    @FXML
    private void handleCancelTransaction() {
        if (com.aulkhami.pakupos.app.utils.AlertHelper.showConfirmation("Batalkan Transaksi", "Apakah Anda yakin ingin membatalkan transaksi ini? Semua barang di keranjang akan dihapus.")) {
            model.getCart().clear();
            if (customerNameField != null) {
                customerNameField.clear();
            }
            posTabPane.getSelectionModel().select(0);
        }
    }

    @FXML
    private void setExactCash() {
        cashInputField.setText(currentGrandTotal.setScale(0, java.math.RoundingMode.HALF_UP).toPlainString());
    }

    @FXML
    private void setFiftyThousand() {
        cashInputField.setText("50000");
    }

    @FXML
    private void setOneHundredThousand() {
        cashInputField.setText("100000");
    }

    private void calculateChange() {
        if (cashInputField == null || changeLabel == null) {
            return;
        }
        try {
            String text = cashInputField.getText().replaceAll("[^\\d.]", "");
            if (text.isEmpty()) {
                changeLabel.setText("Rp 0");
                return;
            }
            BigDecimal cash = new BigDecimal(text);
            BigDecimal change = cash.subtract(currentGrandTotal);
            if (change.compareTo(BigDecimal.ZERO) < 0) {
                changeLabel.setText("Rp 0");
            } else {
                changeLabel.setText(com.aulkhami.pakupos.app.utils.CurrencyHelper.formatRupiah(change));
            }
        } catch (NumberFormatException e) {
            changeLabel.setText("Rp 0");
        }
    }

    @FXML
    private void handleCheckout() {
        interactor.checkout();
        posTabPane.getSelectionModel().select(0);
    }

    @FXML
    private void handleBack() {
        try {
            App.navigate("dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
