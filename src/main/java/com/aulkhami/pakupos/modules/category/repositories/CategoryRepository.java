package com.aulkhami.pakupos.modules.category.repositories;

import com.aulkhami.pakupos.database.DatabaseConnection;
import com.aulkhami.pakupos.modules.category.entities.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category ORDER BY id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void save(Category c) {
        String sql = "INSERT INTO category (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal: " + e.getMessage());
        }
    }
    
    public void update(Category c) {
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setInt(2, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal: " + e.getMessage());
        }
    }
    
    public void delete(Integer id) {
        String sql = "DELETE FROM category WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Kategori tidak ditemukan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal: Kategori ini masih digunakan oleh produk.");
        }
    }
}
