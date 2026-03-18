package com.wiss.quizbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import javax.management.relation.Role;

public class AppUser {

    //Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotBlank
    @Size(max=50)
    @Column(unique=true, nullable = false, length = 50)
    private   String    username;

    @NotBlank
    @Size(max=40)
    @Column(unique=true, nullable = false, length=100)
    private String email;


    @NotBlank
    @Column(nullable=false)
    private String password;


    @NotNull
    @Enumerated(EnumType.STRING) // WICHTIG: Speichert den Namen des Enums ("ADMIN") statt der Zahl (0)
    @Column(nullable = false)
    private Role role;


    public AppUser(String email, String password, Role role, String username) {
        this.email = email;
        this.password= password;
        this.role = role;
        this.username = username;
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
}
