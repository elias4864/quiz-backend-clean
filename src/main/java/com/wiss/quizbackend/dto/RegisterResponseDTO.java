package com.wiss.quizbackend.dto;

public class RegisterResponseDTO {
    private final Long id;
    private final String username;
    private final String email;
    private final String role;
    private final String message;


    public RegisterResponseDTO(Long id, String username, String email, String role, String message) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "RegisterResponseDTO{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
