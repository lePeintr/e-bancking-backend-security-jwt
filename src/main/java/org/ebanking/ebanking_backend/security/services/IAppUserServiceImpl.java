package org.ebanking.ebanking_backend.security.services;

import lombok.AllArgsConstructor;
import org.ebanking.ebanking_backend.security.entities.AppRole;
import org.ebanking.ebanking_backend.security.entities.AppUser;
import org.ebanking.ebanking_backend.security.repositories.IAppRoleRepository;
import org.ebanking.ebanking_backend.security.repositories.IAppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * IAppUserServiceImpl.java
 * <p>
 * Auteur: Administrateur
 */
@Service
@Transactional
@AllArgsConstructor
public class IAppUserServiceImpl implements IAppUserService {
    private IAppUserRepository appUserRepository;
    private IAppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
      AppUser appUser = appUserRepository.findByUsername(username);
      if(appUser!=null){
          throw new RuntimeException("L'utilisateur existe déja");
      }
       if (!password.equals(confirmPassword)){
           throw new RuntimeException("Les deux mots de passe ne correspondent pas");
       }
        appUser= AppUser.builder()
               .userId(UUID.randomUUID().toString())
               .username(username)
               .password(passwordEncoder.encode(password))
               .email(email).build();
       AppUser savedUser =  appUserRepository.save(appUser);
        return savedUser;
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if(appRole!=null) throw new RuntimeException("Ce role existe deja");
        appRole= AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().add(appRole);
        //appUserRepository.save(appUser); on n'est pas obligé de faire ce save car la méthode est transactionnelle doc quand on appelle
        //la methode addToUSer il va demarer la transaction quand on modifie l'utilisateur uil va faire commit il va Mettre ajour automatiquement les LAJ
        //dans la base de données

    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
