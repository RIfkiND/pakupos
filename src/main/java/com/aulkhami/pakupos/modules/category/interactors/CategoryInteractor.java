package com.aulkhami.pakupos.modules.category.interactors;

import com.aulkhami.pakupos.app.interactors.Interactor;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import com.aulkhami.pakupos.modules.category.dtos.CategoryDTO;
import com.aulkhami.pakupos.modules.category.models.CategoryModel;
import com.aulkhami.pakupos.modules.category.services.CategoryService;

public class CategoryInteractor implements Interactor {
    private final CategoryModel model;
    private final CategoryService service = new CategoryService();

    public CategoryInteractor(CategoryModel model) {
        this.model = model;
    }

    public void loadCategories() {
        try {
            model.getCategories().setAll(service.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveCategory(CategoryDTO dto) {
        try {
            service.save(dto);
            loadCategories();
            AlertHelper.showSuccess("Sukses", "Kategori berhasil disimpan!");
        } catch (Exception e) {
            AlertHelper.showError("Error", e.getMessage());
        }
    }

    public void updateCategory(CategoryDTO dto) {
        try {
            service.update(dto);
            loadCategories();
            AlertHelper.showSuccess("Sukses", "Kategori berhasil diperbarui!");
        } catch (Exception e) {
            AlertHelper.showError("Error", e.getMessage());
        }
    }

    public void deleteCategory(Integer id) {
        try {
            service.delete(id);
            loadCategories();
            AlertHelper.showSuccess("Sukses", "Kategori berhasil dihapus!");
        } catch (Exception e) {
            AlertHelper.showError("Error", e.getMessage());
        }
    }
}
