package com.aulkhami.pakupos.modules.user.repositories;

import com.aulkhami.pakupos.app.dao.interfaces.IDAO;
import com.aulkhami.pakupos.modules.user.entities.User;
import java.util.Optional;

public interface IUserRepository extends IDAO<User, Long> {
    Optional<User> findByEmail(String email);
}

