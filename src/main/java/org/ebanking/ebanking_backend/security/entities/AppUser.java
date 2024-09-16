package org.ebanking.ebanking_backend.security.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * AppUser.java
 * <p>
 * Auteur: Administrateur
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AppUser {
    @Id
    private String userId;
    @Column(unique=true)//on ne peut pas avoir plusieurs users qui ont le meme username
    private String username;
    private String password;
    @Column(unique=true)
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)//a chque foid qu'on demande de charger un AppUSer il va charger touts ses attribus et tous ses roles
    private List<AppRole> roles;
}
