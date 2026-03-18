package com.wiss.quizbackend.entity;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

public enum Role {
    ;


    private String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Role{" +
                "description='" + description + '\'' +
                '}';
    }
}
