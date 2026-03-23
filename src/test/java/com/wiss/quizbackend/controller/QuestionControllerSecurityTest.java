package com.wiss.quizbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiss.quizbackend.entity.Question;
import com.wiss.quizbackend.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        Question testQuestion = new Question(
                "Was ist die Hauptstadt von Frankreich?",
                "Paris",
                List.of("London", "Berlin", "Rom"),
                "geography",
                "easy",
                null
        );
        questionRepository.save(testQuestion);
    }

    // ==================== Tests ohne Authentication ====================

    @Test
    void getAllQuestions_withoutAuth_shouldReturn401() throws Exception {
        mockMvc.perform(get("/api/questions"))
                .andExpect(status().isUnauthorized());
    }

    // ==================== Tests als PLAYER ====================

    @Test
    @WithMockUser(username = "player1", roles = {"PLAYER"})
    void createQuestion_asPlayer_shouldReturn403() throws Exception {
        String frage = """
                {
                    "question": "Was bedeutet UML?",
                    "correctAnswer": "Unified Modeling Language",
                    "incorrectAnswers": ["Universal Made Language", "Unit Mark Language"],
                    "category": "it",
                    "difficulty": "medium"
                }
                """;

        mockMvc.perform(post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(frage))
                .andExpect(status().isForbidden()); // Player dürfen keine Fragen erstellen
    }




    @Test
    @WithMockUser(username = "player2", roles = {"PLAYER"})
    void createQuestion_asPlayer_shouldReturn404() throws Exception {
        String frage = """
                {
                    "question": "Was ist  Java?",
                    "correctAnswer": "OOP",
                    "incorrectAnswers": ["Framework", "Rest-API", "Schnittstelle"],
                    "category": "programmiersprache",
                    "difficulty": "medium"
                }
                """;

        mockMvc.perform(post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(frage))
                .andExpect(status().isForbidden()); // Player dürfen keine Fragen erstellen
    }



    // ==================== Tests als ADMIN ====================

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createQuestion_asAdmin_shouldReturn201() throws Exception {
        // Hinweis: Prüfe in deiner Entity, ob das Feld 'incorrectAnswers' oder 'answers' heisst!
        String questionJson = """
                {
                    "question": "Wie heisst die Hauptstadt von Deutschland?",
                    "correctAnswer": "Berlin",
                    "incorrectAnswers": ["Paris", "London", "Rom"],
                    "category": "geography",
                    "difficulty": "easy"
                }
                """;

        mockMvc.perform(post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionJson))
                .andExpect(status().isCreated()); // 201 Created erwartet
    }




    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getAllQuestions_asAdmin_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/questions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}

