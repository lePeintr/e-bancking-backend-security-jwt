package org.ebanking.ebanking_backend.security.repositories;

import org.ebanking.ebanking_backend.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AppUserRepository.java
 * <p>
 * Auteur: Administrateur
 */
public interface IAppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);
}
