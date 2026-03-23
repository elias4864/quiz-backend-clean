package com.wiss.quizbackend.service;

import com.wiss.quizbackend.dto.QuestionDTO;
import com.wiss.quizbackend.entity.Question;
import com.wiss.quizbackend.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    /**
     * Test 1: Alle Fragen abrufen



    /**
     * Test 2: Frage nach ID finden
     */

    @Test
    public void whenGetAllQuestions_thenReturnAllQuestions() {
        // Arrange - Mock-Verhalten definieren
        Question q1 = createTestQuestion("Frage 1", "Antwort 1");
        Question q2 = createTestQuestion("Frage 2", "Antwort 2");
        Question q3 = createTestQuestion("Frage 3", "Antwort ");


        List<Question> mockQuestions = Arrays.asList(q1, q2,q3);

        when(questionRepository.findAll()).thenReturn(mockQuestions);

        // Act - Service-Methode aufrufen
        List<QuestionDTO> result = questionService.getAllQuestionsAsDTO();

        // Assert - Ergebnis prüfen
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getQuestion()).isEqualTo("Frage 1");
        assertThat(result.get(1).getQuestion()).isEqualTo("Frage 2");
        assertThat(result.get(2).getQuestion()).isEqualTo("Frage 3");



        // Verify - Repository-Aufruf prüfen
        verify(questionRepository, times(1)).findAll();
    }


    @Test
    public void whenGetQuestionById_thenReturnQuestion() {

        Long questiondId = 1L;
        Question mockQuestion2 = createTestQuestion("Test Frage 2 ", "Beispielantwort");
        mockQuestion2.setId(questiondId);
        when(questionRepository.findById(questiondId)).thenReturn(Optional.of(mockQuestion2));
        Question result2= questionService.getQuestionById(questiondId);
        assertThat(result2).isEqualTo(mockQuestion2);
        // Arrange
        Long questionId2 = 2L;
        Question mockQuestion = createTestQuestion("Test Frage", "Test Antwort");
        mockQuestion.setId(questionId2);

        when(questionRepository.findById(questionId2)).thenReturn(Optional.of(mockQuestion));

        // Act
        Question result = questionService.getQuestionById(questionId2);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(questionId2);
        assertThat(result.getQuestion()).isEqualTo("Test Frage");

        verify(questionRepository, times(1)).findById(questionId2);
    }

    /**
     * Test 3: Frage mit ungültiger ID - Exception werfen
     */
    @Test
    public void whenGetQuestionByInvalidId_thenThrowException() {
        // Arrange
        Long invalidId = 999L;
        when(questionRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert - Exception erwarten
        assertThatThrownBy(() -> questionService.getQuestionById(invalidId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Question with ID: "+ invalidId +" not found");

        verify(questionRepository, times(1)).findById(invalidId);
    }

    /**
     * Test 4: Neue Frage erstellen
     */
    @Test
    public void whenCreateQuestion_thenReturnSavedQuestion() {
        // Arrange
        Question newQuestion = createTestQuestion("Neue Frage", "Neue Antwort");
        Question savedQuestion = createTestQuestion("Neue Frage", "Neue Antwort");
        savedQuestion.setId(1L);
        //Beispielantwort
        QuestionDTO newQuestionDTO = new QuestionDTO(
                null,
                "Neue Frage",
                "Neue Antwort",
                Arrays.asList("Falsch 1", "Falsch 2", "Falsch 3"),
                "geography",
                "hard"
        );

        when(questionRepository.save(any(Question.class))).thenReturn(savedQuestion);

        // Act
        QuestionDTO result = questionService.createQuestion(newQuestionDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getQuestion()).isEqualTo("Neue Frage");

        verify(questionRepository, times(1)).save(any(Question.class));
    }

    /**
     * Test 5: Frage löschen
     */
    @Test
    public void whenDeleteQuestion_thenQuestionIsDeleted() {
        // Arrange
        Long questionId = 1L;
        Question existingQuestion = createTestQuestion("Zu löschende Frage", "Antwort");
        existingQuestion.setId(questionId);

        when(questionRepository.existsById(questionId)).thenReturn(true);
        doNothing().when(questionRepository).deleteById(questionId);

        // Act
        questionService.deleteQuestion(questionId);

        // Assert - Verify dass deleteById aufgerufen wurde
        verify(questionRepository, times(1)).existsById(questionId);








        Long questionId2 = 2L;
        Question frage2= createTestQuestion("Was ist die Hauptstadt von Frankreich?", "Paris");
        frage2.setId(questionId2);

        when(questionRepository.existsById(questionId2)).thenReturn(true);
        doNothing().when(questionRepository).deleteById(questionId2);

        // Act
        questionService.deleteQuestion(questionId2);

        // Assert - Verify dass deleteById aufgerufen wurde
        verify(questionRepository, times(1)).existsById(questionId2);





    }

    /**
     * Test 6: Fragen nach Kategorie finden
     */
    @Test
    public void whenGetQuestionsByCategory_thenReturnFilteredQuestions() {
        // Arrange
        String category = "sports";
        Question sportQuestion = createTestQuestion("Sport Frage", "Sport Antwort");
        sportQuestion.setCategory(category);

        List<Question> mockQuestions = Arrays.asList(sportQuestion);
        when(questionRepository.findByCategory(category)).thenReturn(mockQuestions);

        // Act
        List<QuestionDTO> result = questionService.getQuestionsByCategoryAsDTO(category);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo(category);

        verify(questionRepository, times(1)).findByCategory(category);



        //Arrange
        String geographycat= "geography";
        Question geographycategory = createTestQuestion("Was ist die Hauptstadt von Frankreich?", "Paris");
        geographycategory.setCategory(geographycat);
        List<Question> mockquetions1 = Arrays.asList(geographycategory);
        //When
        when(questionRepository.findByCategory(category)).thenReturn(mockquetions1);

        //Assert
        assertThat(result).hasSize(37);
        assertThat(result.get(0).getCategory()).isEqualTo(category);

        //Act
        verify(questionRepository, times(1)).findByCategory(geographycat);




        //Arrange
        String sciencecat= "science";
        Question sciencecategory = createTestQuestion("Science Frage", "Science Antwort");
        sciencecategory.setCategory(category);
        List<Question> mockquetions = Arrays.asList(sciencecategory);
        //When
        when(questionRepository.findByCategory(category)).thenReturn(mockquetions);

        //Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo(category);

        //Act
        verify(questionRepository, times(1)).findByCategory(sciencecat);


    }

    /**
     * Test 7: Frage aktualisieren
     */
    @Test
    public void whenUpdateQuestion_thenReturnUpdatedQuestion() {
        // Arrange
        Long questionId = 1L;
        QuestionDTO updatedDTO = new QuestionDTO(
                questionId,
                "Neue Frage",
                "Neue Antwort",
                Arrays.asList("Falsch 1", "Falsch 2", "Falsch 3"),
                "geography",
                "hard"
        );







        Question existingQuestion = createTestQuestion("Alte Frage", "Alte Antwort");
        existingQuestion.setId(questionId);

        Question updatedQuestion = createTestQuestion("Neue Frage", "Neue Antwort");
        updatedQuestion.setId(questionId);
        updatedQuestion.setCategory("geography");
        updatedQuestion.setDifficulty("hard");


        when(questionRepository.existsById(questionId)).thenReturn(true);
        when(questionRepository.save(any(Question.class))).thenReturn(updatedQuestion);


        // Act
        QuestionDTO result = questionService.updateQuestion(questionId, updatedDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getQuestion()).isEqualTo("Neue Frage");
        assertThat(result.getCorrectAnswer()).isEqualTo("Neue Antwort");

        verify(questionRepository, times(1)).existsById(questionId);
        verify(questionRepository, times(1)).save(any(Question.class));
    }

    /**
     * Helper-Methode zum Erstellen von Test-Fragen
     */
    private Question createTestQuestion(String questionText, String correctAnswer) {
        Question question = new Question(
                questionText,
                correctAnswer,
                Arrays.asList("Falsch 1", "Falsch 2", "Falsch 3"),
                "sports",
                "medium",
                null
        );
        return question;
    }
}
