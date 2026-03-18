package com.wiss.quizbackend.service;

import com.wiss.quizbackend.dto.QuestionDTO;
import com.wiss.quizbackend.dto.QuestionFormDTO;
import com.wiss.quizbackend.entity.Question;
import com.wiss.quizbackend.dto.QuestionDTO;
import com.wiss.quizbackend.dto.QuestionFormDTO;
import com.wiss.quizbackend.exception.CategoryNotFoundException;
import com.wiss.quizbackend.exception.DifficultyNotFoundException;
import com.wiss.quizbackend.exception.QuestionNotFoundException;
import com.wiss.quizbackend.mapper.QuestionMapper;
import com.wiss.quizbackend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    // --- Lese-Operationen ---

    public List<QuestionDTO> getAllQuestionsAsDTO() {
        return QuestionMapper.toDTOList(repository.findAll());
    }

    public List<QuestionFormDTO> getAllQuestionsAsFormDTO() {
        // Nutzt jetzt die neu erstellte Methode im Mapper
        return QuestionMapper.toFormDTOList(repository.findAll());
    }


    public QuestionDTO getQuestionByIdAsDTO(Long id) {
        return QuestionMapper.toDTO(getQuestionById(id));
    }

    public QuestionFormDTO getQuestionByIdAsFormDTO(Long id) {
        return QuestionMapper.toFormDTO(getQuestionById(id));
    }

    // --- Filter & Suche (Neu hinzugefügt für Controller-Support) ---

    public List<QuestionDTO> getQuestionsByCategoryAsDTO(String category) {
        validateCategory(category);
        return QuestionMapper.toDTOList(repository.findByCategory(category.toLowerCase()));
    }

    public List<QuestionDTO> getQuestionsByDifficultyAsDTO(String difficulty) {
        return QuestionMapper.toDTOList(repository.findByDifficulty(difficulty.toLowerCase()));
    }

    public List<QuestionDTO> getQuestionsByCategoryAndDifficulty(String category, String difficulty) {
        return QuestionMapper.toDTOList(repository.findByCategoryAndDifficulty(category, difficulty));
    }

    public List<QuestionDTO> searchQuestions(String query) {
        return QuestionMapper.toDTOList(repository.findByQuestionContainingIgnoreCase(query));
    }

    // --- Statistik & Zufall (Neu hinzugefügt für Controller-Support) ---

    public long getTotalQuestionsCount() {
        return repository.count();
    }

    public long getQuestionCountByCategory(String category) {
        validateCategory(category);
        return repository.countByCategory(category);
    }

    public List<QuestionDTO> getRandomQuestions(int limit) {
        return QuestionMapper.toDTOList(repository.findRandomQuestions(limit));
    }

    public List<QuestionDTO> getRandomQuestionsByCategory(String category, int limit) {
        validateCategory(category);
        return QuestionMapper.toDTOList(repository.findRandomByCategory(category, limit));
    }

    // --- Schreib-Operationen ---

    public QuestionDTO createQuestion(QuestionDTO dto) {
        validateDto(dto);
        Question entity = QuestionMapper.toEntity(dto);
        return QuestionMapper.toDTO(repository.save(entity));
    }

    public QuestionDTO updateQuestion(Long id, QuestionDTO dto) {
        if (!repository.existsById(id)) {
            throw new QuestionNotFoundException(id);
        }
        Question entity = QuestionMapper.toEntity(dto);
        entity.setId(id);
        return QuestionMapper.toDTO(repository.save(entity));
    }

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

    // --- Löschen & Hilfsmethoden ---

    public void deleteQuestion(Long id) {
        if (!repository.existsById(id)) {
            throw new QuestionNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public Question getQuestionById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
    }

    // --- Validierungen ---

    private void validateDto(QuestionDTO dto) {
        if (dto == null || dto.getQuestion() == null || dto.getQuestion().trim().isEmpty()) {
            throw new IllegalArgumentException("Ungültige Fragendaten.");
        }
    }

    private void validateCategory(String category) {
        List<String> valid = List.of("sports", "games", "movies", "geography", "science", "history");
        if (category == null || !valid.contains(category.toLowerCase())) {
            throw new CategoryNotFoundException(category);
        }
    }
}