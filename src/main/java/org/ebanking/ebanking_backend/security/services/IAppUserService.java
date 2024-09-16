package org.ebanking.ebanking_backend.security.services;

import org.ebanking.ebanking_backend.security.entities.AppRole;
import org.ebanking.ebanking_backend.security.entities.AppUser;

public interface IAppUserService {
    AppUser addNewUser(String username, String password,String email, String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username,String role);
    void removeRoleFromUser(String username,String role);
    AppUser loadUserByUsername(String username);
}
