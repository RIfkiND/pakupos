package com.aulkhami.pakupos.dao;

import com.aulkhami.pakupos.database.DatabaseConnection;
import com.aulkhami.pakupos.enums.OrderStatus;
import com.aulkhami.pakupos.models.entities.Order;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY ordered_at DESC";
        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public BigDecimal getTotalSalesToday() {
        String sql =
            "SELECT SUM(total_amount) FROM orders WHERE DATE(ordered_at) = CURRENT_DATE AND status = 'COMPLETED'";
        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                BigDecimal result = rs.getBigDecimal(1);
                return result != null ? result : BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    public int getOrderCountToday() {
        String sql =
            "SELECT COUNT(*) FROM orders WHERE DATE(ordered_at) = CURRENT_DATE";
        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Order save(Order order) {
        String sql =
            "INSERT INTO orders (user_id, customer_name, status) VALUES (?, ?, ?)";
        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            )
        ) {
            ps.setLong(1, order.getUserId());
            ps.setString(2, order.getCustomerName());
            ps.setString(3, order.getStatus().name());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    order.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setUserId(rs.getLong("user_id"));
        order.setOrderCode(rs.getString("order_code"));
        order.setQueueNumber(rs.getInt("queue_number"));
        order.setCustomerName(rs.getString("customer_name"));
        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        order.setStatus(OrderStatus.valueOf(rs.getString("status")));
        order.setOrderedAt(rs.getTimestamp("ordered_at"));
        order.setCreatedAt(rs.getTimestamp("created_at"));
        order.setUpdatedAt(rs.getTimestamp("updated_at"));
        return order;
    }
}
