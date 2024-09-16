package org.ebanking.ebanking_backend.security.repositories;

import org.ebanking.ebanking_backend.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppRoleRepository extends JpaRepository<AppRole,String> {
}
