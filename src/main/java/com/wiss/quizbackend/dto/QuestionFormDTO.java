package com.wiss.quizbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class QuestionFormDTO {
    private Long id;
    private String question;
    private String correctAnswer;
    private List<String> incorrectAnswers;
    private String category;
    private String difficulty;

    // NEU: Felder für den Ersteller
    @Schema(description = "Benutzername des Erstellers", example = "john_doe")
    private String creatorUsername;

    @Schema(description = "ID des Erstellers", example = "42")
    private Long creatorId;

    // Standard-Konstruktor
    public QuestionFormDTO() {}

    // NEU: Vollständiger Konstruktor inklusive Ersteller-Infos
    public QuestionFormDTO(Long id, String question, String correctAnswer,
                           List<String> incorrectAnswers, String category,
                           String difficulty, String creatorUsername, Long creatorId) {
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
        this.category = category;
        this.difficulty = difficulty;
        this.creatorUsername = creatorUsername;
        this.creatorId = creatorId;
    }

    // Getter und Setter
    public String getCreatorUsername() { return creatorUsername; }
    public void setCreatorUsername(String creatorUsername) { this.creatorUsername = creatorUsername; }

    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }

    // ... (restliche Getter/Setter für question, correctAnswer etc. beibehalten)
}