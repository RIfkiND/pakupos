package com.aulkhami.pakupos.modules.category.services;

import com.aulkhami.pakupos.modules.category.repositories.CategoryRepository;
import com.aulkhami.pakupos.modules.category.dtos.CategoryDTO;
import com.aulkhami.pakupos.modules.category.entities.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryService {
    private final CategoryRepository repo = new CategoryRepository();

    public List<CategoryDTO> getAll() {
        return repo.findAll().stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    public void save(CategoryDTO dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong.");
        }
        Category c = new Category();
        c.setName(dto.getName());
        repo.save(c);
    }

    public void update(CategoryDTO dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong.");
        }
        Category c = new Category();
        c.setId(dto.getId());
        c.setName(dto.getName());
        repo.update(c);
    }

    public void delete(Integer id) {
        repo.delete(id);
    }
}
