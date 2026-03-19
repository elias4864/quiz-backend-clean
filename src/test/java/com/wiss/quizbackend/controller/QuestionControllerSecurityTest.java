
        package com.wiss.quizbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiss.quizbackend.entity.Question;
import com.wiss.quizbackend.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet
        .AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result
        .MockMvcResultMatchers.*;

/**
 * Integration Tests für die Security der Question-Endpoints.
 * Diese Tests prüfen:
 * - Authentifizierung: Können nur eingeloggte User zugreifen?
 * - Autorisierung: Haben die Rollen die richtigen Berechtigungen?
 * - HTTP Status Codes: Werden die richtigen Statuscodes zurückgegeben?
 */
@SpringBootTest
@AutoConfigureMockMvc

public class QuestionControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ObjectMapper objectMapper;



    @BeforeEach
    void setUp() {
        questionRepository.deleteAll();

        // Wir erstellen die Entity so, wie sie in der Datenbank definiert ist:
        // Die Entity hat 'incorrectAnswers' (meistens 3 Stück)
        Question testQuestion = new Question(
                "Was ist die Hauptstadt von Frankreich?", // Frage
                "Paris",                                   // Richtige Antwort
                List.of("London", "Berlin", "Rom"),        // Falsche Antworten (Liste)
                "geography",                               // Kategorie
                "easy",                                    // Schwierigkeit
                null                                       // User
        );

        questionRepository.save(testQuestion);
    }
    // ==================== Tests ohne Authentication ====================

    @Test
    void getAllQuestions_withoutAuth_shouldReturn401() throws Exception {
        mockMvc.perform(get("/api/questions"))

                .andExpect(status().isUnauthorized());// Da CSRF oft 403 wirft, wenn nicht eingeloggt
    }

    // ==================== Tests als PLAYER ====================

    @Test
    @WithMockUser(username = "player1", roles = {"PLAYER"})
    void createQuestion_asPlayer_shouldReturn403() throws Exception {
        // Wir senden valide Daten, damit nicht 400 (Validation) sondern 403 (Security) kommt
        String questionJson = """
                {
                    "question": "Wie heisst die Hauptstadt der Schweiz?",
                    "correctAnswer": "Bern",
                    "answers": ["Bern", "Zürich", "Basel", "Genf"],
                    "category": "geography",
                    "difficulty": "easy"
                }
                """;

        mockMvc.perform(post("/api/questions") // Nutze den Endpunkt aus dem Controller
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionJson))
                .andExpect(status().isForbidden());
    }

    // ==================== Tests als ADMIN ====================

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createQuestion_asAdmin_shouldReturn201() throws Exception {
        String questionJson = """
                {
                    "question": "Wie heisst die Hauptstadt von Frankreich?",
                    "correctAnswer": "Paris",
                    "answers": ["Paris", "London", "Berlin", "Rom"],
                    "category": "geography",
                    "difficulty": "easy"
                }
                """;

        mockMvc.perform(post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.question").value("Wie heisst die Hauptstadt von Frankreich?"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteQuestion_asAdmin_shouldReturn204() throws Exception {
        Question saved = questionRepository.findAll().get(0);
        Long questionId = saved.getId();

        mockMvc.perform(delete("/api/questions/" + questionId))
                .andExpect(status().isNoContent());
    }
}