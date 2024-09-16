package org.ebanking.ebanking_backend.security.services;

import lombok.AllArgsConstructor;
import org.ebanking.ebanking_backend.security.entities.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsServiceImpl.java
 * <p>
 * Auteur: Administrateur
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private IAppUserService appUserService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       AppUser appUser =   appUserService.loadUserByUsername(username); //je dis a Spring security quelle est la méthode que je veux appeler pour
        //recuperer l'utilisateur
       if(appUser==null) throw new UsernameNotFoundException(String.format("User %s not found",username));

       String[]roles =  appUser.getRoles().stream().map(u->u.getRole()).toArray(String[]::new);
    //une fois que j'ai récupéré les dnnées de l'utilisateur à partir de ma bd, je les transfert ces données dans un objet de type
        //USerDetails car SpringSecurity exige que cette methode renvoie un type UserDetails
       UserDetails userDetails= User
               .withUsername(appUser.getUsername())
               .password(appUser.getPassword())
               .authorities(roles)
               .build();
        return userDetails;
    }
}
