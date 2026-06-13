package com.aulkhami.pakupos.dao.interfaces;

import com.aulkhami.pakupos.models.entities.User;
import java.util.Optional;

public interface IUserDAO extends IDAO<User, Long> {
    Optional<User> findByEmail(String email);
}
