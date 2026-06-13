package com.aulkhami.pakupos.models.entities;

import com.aulkhami.pakupos.enums.UserRole;
import java.sql.Timestamp;

public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private UserRole role;
    private Boolean isActive;
    private Timestamp lastLoginAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public User() {
        this.isActive = true;
        this.role = UserRole.KARYAWAN;
    }

    public User(
        String name,
        String email,
        String password,
        String phone,
        UserRole role
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.isActive = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Timestamp getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Timestamp lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return (
            "User{" +
            "id=" +
            id +
            ", name='" +
            name +
            '\'' +
            ", email='" +
            email +
            '\'' +
            ", role=" +
            role +
            '}'
        );
    }
}
