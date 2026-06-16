package com.aulkhami.pakupos.modules.pos.repositories;

import com.aulkhami.pakupos.database.DatabaseConnection;
import com.aulkhami.pakupos.modules.pos.entities.OrderItem;

import java.sql.*;

public class OrderItemRepository {

    public OrderItem save(OrderItem item) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price, subtotal) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            ps.setLong(1, item.getOrderId());
            ps.setLong(2, item.getProductId());
            ps.setInt(3, item.getQuantity());
            ps.setBigDecimal(4, item.getUnitPrice());
            ps.setBigDecimal(5, item.getSubtotal());
            
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    item.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not save order item", e);
        }
        return item;
    }
}
