package com.wiss.quizbackend.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {


    @NotBlank(message = "Username oder Email ist erforderlich")
    private String usernameOrEmail;

    @NotBlank(message = "Passwort ist erforderlich")
    private String password;

    // Default Constructor für JSON Deserialization
    public LoginRequestDTO() {}

    // Constructor mit allen Feldern (für Tests)
    public LoginRequestDTO(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    // Getters und Setters
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequestDTO{" +
                "password='" + password + '\'' +
                ", usernameOrEmail='" + usernameOrEmail + '\'' +
                '}';
    }
}
