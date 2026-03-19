package com.wiss.quizbackend.entity;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "questions")

public class Question {

    @Id                                    // ← "Das ist der Primary Key"
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ← "PostgreSQL macht Auto-Increment"
    private Long id;

    @Column(nullable = false, length = 128)  // ← "Spalte darf nicht NULL sein, max 128 Zeichen"
    private String question;

    @Column(name = "correct_answer", nullable = false)  // ← "Spalte heisst 'correct_answer'"
    private String correctAnswer;

    @ElementCollection                     // ← "Liste wird als separate Tabelle gespeichert"
    @CollectionTable(name = "question_incorrect_answers", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "incorrect_answer")
    private List<String> incorrectAnswers;

    @Column(nullable = false, length = 64)
    private String category;

    @Column(nullable = false, length = 32)
    private String difficulty;

    @ManyToOne(fetch = FetchType.LAZY)          // Viele Fragen können von einem User erstellt werden, Lazy Loading
    @JoinColumn(name = "created_by_user_id")    // Fremdschlüssel-Spalte in der "questions" Tabelle
    private AppUser createdBy;                  // Der User, der die Frage erstellt hat

    // DEFAULT CONSTRUCTOR hinzufügen (für JPA/Hibernate):
    public Question(){}

    /**
     * Konstruktor ohne ID für neue Fragen
     * @param question die Frage
     * @param correctAnswer die richtige Antwort
     * @param incorrectAnswers Liste der falschen Antworten
     * @param category Kategorie
     * @param difficulty Schwierigkeitsgrad
     * @param createdBy der User, der die Frage erstellt
     */
    public Question(String question, String correctAnswer,
                    List<String> incorrectAnswers, String category,
                    String difficulty, AppUser createdBy) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
        this.category = category;
        this.difficulty = difficulty;
        this.createdBy = createdBy; // Neu, den Ersteller der Frage setzen
    }

    /**
     * Konstruktor mit ID um bestehende Fragen zu aktualisieren oder löschen
     * @param id die ID der Frage
     * @param question die Frage
     * @param correctAnswer die richtige Antwort
     * @param incorrectAnswers Liste der falschen Antworten
     * @param category Kategorie
     * @param difficulty Schwierigkeitsgrad
     * @param createdBy der User, der die Frage erstellt
     */
    public Question(Long id, String question, String correctAnswer,
                    List<String> incorrectAnswers, String category,
                    String difficulty, AppUser createdBy) {
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
        this.category = category;
        this.difficulty = difficulty;
        this.createdBy = createdBy; // Neu, den Ersteller der Frage setzen
    }





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public AppUser getCreatedBy() { return createdBy; }
    public void setCreatedBy(AppUser createdBy) { this.createdBy = createdBy; }
}
