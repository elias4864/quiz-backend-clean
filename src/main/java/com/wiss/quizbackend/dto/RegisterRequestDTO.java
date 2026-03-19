package com.wiss.quizbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequestDTO {


    @NotBlank(message = "Username ist erforderlich")
    @Size(min = 3, max = 50, message = "Username muss zwischen 3 und 50 Zeichen haben")
    private String username;


    @NotBlank(message = "Email ist erforderlich")
    @Email(message = "Email muss ein gültiges Format haben")
    @Size(max = 100, message = "Email darf maximal 100 Zeichen haben")
    private String email;

    @NotBlank(message = "Passwort ist erforderlich")
    @Size(min = 6, message = "Passwort muss mindestens 6 Zeichen haben")
    private String password;


    //Leerer Konstruktor

    public RegisterRequestDTO() {

    }

    public RegisterRequestDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public @NotBlank(message = "Email ist erforderlich") @Email(message = "Email muss ein gültiges Format haben") @Size(max = 100, message = "Email darf maximal 100 Zeichen haben") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email ist erforderlich") @Email(message = "Email muss ein gültiges Format haben") @Size(max = 100, message = "Email darf maximal 100 Zeichen haben") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Passwort ist erforderlich") @Size(min = 6, message = "Passwort muss mindestens 6 Zeichen haben") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Passwort ist erforderlich") @Size(min = 6, message = "Passwort muss mindestens 6 Zeichen haben") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Username ist erforderlich") @Size(min = 3, max = 50, message = "Username muss zwischen 3 und 50 Zeichen haben") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username ist erforderlich") @Size(min = 3, max = 50, message = "Username muss zwischen 3 und 50 Zeichen haben") String username) {
        this.username = username;
    }

    // toString ohne Passwort (Security!)
    @Override
    public String toString() {
        return "RegisterRequestDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='[HIDDEN]'" +
                '}';
    }

}
