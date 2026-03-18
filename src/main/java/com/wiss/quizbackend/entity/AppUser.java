package com.wiss.quizbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
@Entity
@Table(name="app_user")
public class  AppUser  implements UserDetails{

    //Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotBlank
    @Size(max=50)
    @Column(unique=true, nullable = false, length = 50)
    private   String username;

    @NotBlank
    @Size(max=40)
    @Column(unique=true, nullable = false, length=100)
    private String email;




    @NotNull
    @Enumerated(EnumType.STRING) // WICHTIG: Speichert den Namen des Enums ("ADMIN") statt der Zahl (0)
    @Column(nullable = false)
    private Role role;




    @NotBlank
    @Column(nullable=false)
    private String password;





//Neuer User hinzufügen
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private AppUser createdBy;








    public AppUser(String email, String password, Role role, String username) {
        this.email = email;
        this.password= password;
        this.role = role;
        this.username = username;
    }


    public enum   Role {

        Admin, PLAYER

    }

    //Automatischer Konstruktor
    public AppUser() {

    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // "ROLE_" Prefix für Spring Security hinzufügen
        // Aus Role.ADMIN wird "ROLE_ADMIN"
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }






    public Long getId() {
        return id;
    }

    public @NotBlank @Size(max = 40) String getEmail() {
        return email;
    }


    public void setEmail(@NotBlank @Size(max = 40) String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password; // Gibt den BCrypt Hash zurück
    }
    @Override
    public String getUsername() {
        return username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true; // Konto läuft nie ab
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Konto ist nie gesperrt
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Passwort läuft nie ab
    }

    @Override
    public boolean isEnabled() {
        return true; // Konto ist immer aktiv
    }



    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public @NotNull Role getRole() {
        return role;
    }


    public void setUsername(@NotBlank @Size(max = 50) String username) {
        this.username = username;
    }

    public void setRole(@NotNull Role role) {
        this.role = role;
    }

    public AppUser getCreatedBy() {
        return createdBy;
    }


    public void setCreatedBy(AppUser createdBy) {
        this.createdBy = createdBy;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
