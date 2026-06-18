package com.aulkhami.pakupos.modules.pos.services;

import com.aulkhami.pakupos.app.enums.OrderStatus;
import com.aulkhami.pakupos.modules.inventory.dtos.ProductResponseDTO;
import com.aulkhami.pakupos.modules.inventory.services.InventoryService;
import com.aulkhami.pakupos.modules.pos.dtos.CartItemDTO;
import com.aulkhami.pakupos.modules.pos.dtos.OrderRequestDTO;
import com.aulkhami.pakupos.modules.pos.dtos.OrderResponseDTO;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import com.aulkhami.pakupos.modules.pos.entities.OrderItem;
import com.aulkhami.pakupos.modules.pos.repositories.OrderItemRepository;
import com.aulkhami.pakupos.modules.pos.repositories.OrderRepository;
import com.aulkhami.pakupos.modules.user.entities.User;
import com.aulkhami.pakupos.app.utils.SessionManager;

import java.util.List;

public class POSService {

    private final InventoryService inventoryService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public POSService() {
        this.inventoryService = new InventoryService();
        this.orderRepository = new OrderRepository();
        this.orderItemRepository = new OrderItemRepository();
    }

    public List<ProductResponseDTO> getCatalog() {
        return inventoryService.getAllProducts();
    }

    public List<ProductResponseDTO> searchCatalog(String keyword) {
        return inventoryService.searchProducts(keyword);
    }

    public OrderResponseDTO processCheckout(OrderRequestDTO request) {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("User must be logged in to process an order");
        }

        if (request.getCartItems() == null || request.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Cart cannot be empty");
        }

        if (request.getCustomerName() == null || request.getCustomerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required");
        }

        // 1. Create Order
        Order order = new Order();
        order.setUserId(currentUser.getId());
        order.setCustomerName(request.getCustomerName());
        order.setTotalAmount(request.getTotalAmount());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus(OrderStatus.COMPLETED);

        Order savedOrder = orderRepository.save(order);

        // 2. Create Order Items
        for (CartItemDTO itemDTO : request.getCartItems()) {
            OrderItem item = new OrderItem();
            item.setOrderId(savedOrder.getId());
            item.setProductId(itemDTO.getProduct().getId());
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(itemDTO.getProduct().getPrice());
            item.setSubtotal(itemDTO.getSubtotal());
            
            orderItemRepository.save(item);
        }

        return new OrderResponseDTO(savedOrder);
    }
}
