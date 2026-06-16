package com.aulkhami.pakupos.modules.user.dtos;

import com.aulkhami.pakupos.app.enums.UserRole;
import com.aulkhami.pakupos.modules.user.entities.User;

import java.sql.Timestamp;

public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private UserRole role;
    private Boolean isActive;
    private Timestamp lastLoginAt;

    public UserResponseDTO() {
    }

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = user.getRole();
        this.isActive = user.getIsActive();
        this.lastLoginAt = user.getLastLoginAt();
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
}
