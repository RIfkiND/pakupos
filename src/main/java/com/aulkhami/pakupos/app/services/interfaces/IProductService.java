package com.aulkhami.pakupos.app.services.interfaces;

import com.aulkhami.pakupos.modules.inventory.entities.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Optional<Product> getById(Integer id);

    List<Product> getAll();

    Product save(Product product);

    void update(Product product);

    void delete(Integer id);
}

