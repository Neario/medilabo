package com.medilabo.auth.repository;

import com.medilabo.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link User}.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their login identifier.
     *
     * @param identifier the user's login identifier
     * @return the matching user or empty if none exists
     */
    Optional<User> findByIdentifier(String identifier);
}
