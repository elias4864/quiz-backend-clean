package com.wiss.quizbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiss.quizbackend.config.SecurityConfig;
import com.wiss.quizbackend.dto.QuestionDTO;
import com.wiss.quizbackend.dto.QuestionFormDTO;
import com.wiss.quizbackend.exception.QuestionNotFoundException;
import com.wiss.quizbackend.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(QuestionController.class)
@Import(SecurityConfig.class)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuestionService questionService;

    @Test
    @WithMockUser(roles = "PLAYER")
    public void getAllQuestions_asPlayer_shouldReturn200() throws Exception {
        QuestionDTO q = createTestQuestionDTO(1L, "Test Frage");
        when(questionService.getAllQuestionsAsDTO()).thenReturn(List.of(q));

        mockMvc.perform(get("/api/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].question", is("Test Frage")));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void createQuestion_asAdmin_shouldReturn201() throws Exception {
        // ID null für Input, ID 1L für das Ergebnis vom Mock
        QuestionDTO input = createTestQuestionDTO(null, "Dies ist eine neue Frage");
        QuestionDTO saved = createTestQuestionDTO(1L, "Dies ist eine neue Frage");

        when(questionService.createQuestion(any(QuestionDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1)); // Erwartet 1, da 'saved' ID 1L hat
    }




    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateQuestion_shouldReturn200() throws Exception {
        QuestionDTO updateData = createTestQuestionDTO(1L, "Update der Testfrage");
        when(questionService.updateQuestion(eq(1L), any(QuestionDTO.class))).thenReturn(updateData);

        mockMvc.perform(put("/api/questions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question").value("Update der Testfrage"));
    }


    @Test
    @WithMockUser(roles = "PLAYER")
    public void createQuestion_asPlayer_shouldReturn403() throws Exception {
        // Wir erstellen ein gültiges DTO, damit die Validierung NICHT fehlschlägt
        QuestionDTO validData = createTestQuestionDTO(null, "Dies ist eine Testfrage");

        mockMvc.perform(post("/api/questions")
                        // .with(csrf()) // Nur lassen, wenn CSRF aktiv ist
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validData))) // Gültige Daten senden!
                .andExpect(status().isForbidden()); // Jetzt wird 403 geworfen
    }


    private QuestionDTO createTestQuestionDTO(Long id, String text) {
        return new QuestionDTO(
                id,
                text, // Muss mind. 5 Zeichen haben (z.B. "Test Frage")
                "Bern",
                Arrays.asList("Bern", "Zürich", "Basel", "Genf"), // GENAU 4 Einträge
                "geography", // Muss exakt: sports, games, movies, geography, science, history, biology oder mathematics sein
                "easy"       // Muss exakt: easy, medium oder hard sein
        );
    }
}