package org.ebanking.ebanking_backend.security.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

/**
 * AppRole.java
 * <p>
 * Auteur: Administrateur
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AppRole {
    @Id
    private String role;
}
