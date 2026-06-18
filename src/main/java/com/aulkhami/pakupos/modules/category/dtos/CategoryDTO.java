package com.aulkhami.pakupos.modules.category.dtos;

import com.aulkhami.pakupos.modules.category.entities.Category;

public class CategoryDTO {
    private Integer id;
    private String name;

    public CategoryDTO(Category c) {
        this.id = c.getId();
        this.name = c.getName();
    }

    public CategoryDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
