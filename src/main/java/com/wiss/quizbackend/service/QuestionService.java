package com.wiss.quizbackend.service;

import com.wiss.quizbackend.dto.QuestionDTO;
import com.wiss.quizbackend.dto.QuestionFormDTO;
import com.wiss.quizbackend.entity.Question;
import com.wiss.quizbackend.exception.CategoryNotFoundException;
import com.wiss.quizbackend.exception.DifficultyNotFoundException;
import com.wiss.quizbackend.exception.QuestionNotFoundException;
import com.wiss.quizbackend.mapper.QuestionMapper;
import com.wiss.quizbackend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    // --- Lese-Operationen (DTOs) ---

    public List<QuestionDTO> getAllQuestionsAsDTO() {
        return QuestionMapper.toDTOList(repository.findAll());
    }

    public List<QuestionFormDTO> getAllQuestionsAsFormDTO() {
        return QuestionMapper.toDTOList(repository.findAll());
    }

    public QuestionDTO getQuestionByIdAsDTO(Long id) {
        // Nutzt die interne getQuestionById, die bereits validiert und Exceptions wirft
        return QuestionMapper.toDTO(getQuestionById(id));
    }

    public QuestionFormDTO getQuestionByIdAsFormDTO(Long id) {
        return QuestionMapper.toFormDTO(getQuestionById(id));
    }

    // --- Schreib-Operationen (DTO-basiert) ---

    public QuestionDTO createQuestion(QuestionDTO dto) {
        validateDto(dto);
        Question entity = QuestionMapper.toEntity(dto);
        Question saved = repository.save(entity);
        return QuestionMapper.toDTO(saved);
    }

    public QuestionDTO updateQuestion(Long id, QuestionDTO dto) {
        if (!repository.existsById(id)) {
            throw new QuestionNotFoundException(id);
        }
        Question entity = QuestionMapper.toEntity(dto);
        entity.setId(id); // ID für Update sicherstellen
        return QuestionMapper.toDTO(repository.save(entity));
    }

    // --- Admin-Formular Operationen ---

    public QuestionFormDTO createQuestionFromForm(Question question) {
        return QuestionMapper.toFormDTO(repository.save(question));
    }

    public QuestionFormDTO updateQuestionFromForm(Long id, Question question) {
        if (!repository.existsById(id)) {
            throw new QuestionNotFoundException(id);
        }
        question.setId(id);
        return QuestionMapper.toFormDTO(repository.save(question));
    }

    public void deleteQuestion(Long id) {
        if (!repository.existsById(id)) {
            throw new QuestionNotFoundException(id);
        }
        repository.deleteById(id);
    }

    // --- Interne Logik & Entity-Zugriff ---

    public Question getQuestionById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
    }

    public List<QuestionDTO> getRandomQuestions(int limit) {
        if (limit <= 0 || limit > 50) {
            throw new IllegalArgumentException("Limit must be between 1 and 50");
        }
        return QuestionMapper.toDTOList(repository.findRandomQuestions(limit));
    }

    public List<QuestionDTO> getQuestionsByCategoryAsDTO(String category) {
        validateCategory(category);
        List<Question> entities = repository.findByCategory(category.toLowerCase());
        return QuestionMapper.toDTOList(entities);
    }

    // --- Validierungen ---

    private void validateDto(QuestionDTO dto) {
        if (dto.getQuestion() == null || dto.getQuestion().trim().isEmpty()) {
            throw new IllegalArgumentException("Question text is required");
        }
        if (dto.getCorrectAnswer() == null || dto.getCorrectAnswer().trim().isEmpty()) {
            throw new IllegalArgumentException("Correct answer is required");
        }
    }

    private void validateCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        List<String> valid = List.of("sports", "games", "movies", "geography", "science", "history");
        if (!valid.contains(category.toLowerCase())) {
            throw new CategoryNotFoundException(category);
        }
    }

    private void validateDifficulty(String difficulty) {
        if (difficulty == null || difficulty.trim().isEmpty()) {
            throw new IllegalArgumentException("Difficulty cannot be null");
        }
        List<String> valid = List.of("easy", "medium", "hard");
        if (!valid.contains(difficulty.toLowerCase())) {
            throw new DifficultyNotFoundException(difficulty);
        }
    }
}