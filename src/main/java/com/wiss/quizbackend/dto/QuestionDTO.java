package com.wiss.quizbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

public class QuestionDTO {

    @Schema(description = "Eindeutige ID der Frage", example = "1")
    private Long id;

    @Schema(description = "Der Frage-Text", example = "Was ist die Hauptstadt der Schweiz?")
    @NotBlank(message = "Frage Text ist erforderlich")
    @Size(min = 5, max = 128, message = "Frage muss zwischen 5 und 128 Zeichen sein")
    private String question;

    @Schema(description = "Die korrekte Antwort", example = "Bern")
    @NotBlank(message = "Richtige Antwort ist erforderlich")
    @Size(max = 32, message = "Antwort darf maximal 32 Zeichen haben")
    private String correctAnswer;

    @Schema(description = "Alle Antwortmöglichkeiten (inkl. korrekte)",
            example = "[\"Bern\", \"Zürich\", \"Basel\", \"Genf\"]")
    @NotEmpty(message = "Antworten-Liste darf nicht leer sein")
    @Size(min = 4, max = 4, message = "Es müssen genau 4 Antworten vorhanden sein")
    private List<@NotBlank(message = "Antworten dürfen nicht leer sein") String> answers;

    @Schema(description = "Die Kategorie der Frage", example = "sports")
    @NotBlank(message = "Kategorie ist erforderlich")
    @Pattern(regexp = "sports|games|movies|geography|science|history|biology|mathematics",
            message = "Kategorie muss eine gültige Kategorie sein")
    private String category;

    @Schema(description = "Der Schwierigkeitsgrad der Frage", example = "hard")
    @NotBlank(message = "Schwierigkeitsgrad ist erforderlich")
    @Pattern(regexp = "easy|medium|hard",
            message = "Schwierigkeitsgrad muss einer der folgenden sein: easy, medium, hard")
    private String difficulty;

    // Standard-Konstruktor
    public QuestionDTO() {}

    // Vollständiger Konstruktor
    public QuestionDTO(Long id, String question, String correctAnswer, List<String> answers,
                       String category, String difficulty) {
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
        this.category = category;
        this.difficulty = difficulty;
    }

    // GETTER UND SETTER
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public List<String> getAnswers() { return answers; }
    public void setAnswers(List<String> answers) { this.answers = answers; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
}