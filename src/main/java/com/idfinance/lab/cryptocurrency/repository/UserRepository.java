package com.idfinance.lab.cryptocurrency.repository;

import com.idfinance.lab.cryptocurrency.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The {@link UserRepository} interface provides CRUD operations for managing user entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Checks if a user with the given username exists.
     * @param username The username to check.
     * @return  {@code true} if a user with the given username exists, {@code false} otherwise.
     */
    boolean existsByUsername(String username);
}
