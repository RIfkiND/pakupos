package com.aulkhami.pakupos.app.dao.interfaces;

import java.util.List;
import java.util.Optional;

public interface IDAO<T, ID> {
    Optional<T> findById(ID id);

    List<T> findAll();

    T save(T entity);

    void update(T entity);

    void delete(ID id);
}
