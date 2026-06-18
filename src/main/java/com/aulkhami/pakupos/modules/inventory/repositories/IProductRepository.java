package com.aulkhami.pakupos.modules.inventory.repositories;

import com.aulkhami.pakupos.modules.inventory.entities.Product;
import com.aulkhami.pakupos.app.dao.interfaces.IDAO;
import java.util.List;

public interface IProductRepository extends IDAO<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> search(String keyword);
    List<String> getAllCategories();
}

