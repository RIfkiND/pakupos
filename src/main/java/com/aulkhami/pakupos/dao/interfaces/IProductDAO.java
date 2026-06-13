package com.aulkhami.pakupos.dao.interfaces;

import com.aulkhami.pakupos.models.entities.Product;
import java.util.List;

public interface IProductDAO extends IDAO<Product, Long> {
    List<Product> findByCategory(String category);
}
