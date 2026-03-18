package com.wiss.quizbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import javax.management.relation.Role;
@Entity
@Table(name="appuser")
public class AppUser {

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
    public String toString() {
        return "AppUser{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
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

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public @NotNull Role getRole() {
        return role;
    }

    public @NotBlank @Size(max = 50) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(max = 50) String username) {
        this.username = username;
    }

    public void setRole(@NotNull Role role) {
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
