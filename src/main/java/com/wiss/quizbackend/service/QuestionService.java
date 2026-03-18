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


    public QuestionDTO getQuestionByIdAsDTO(Long id) {
        return QuestionMapper.toDTO(getQuestionById(id));
    }



    // --- Schreib-Operationen (Spieler/API) ---

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

    // --- Admin-Formular Operationen (Entity-basiert) ---

    public QuestionFormDTO createQuestionFromForm(Question question) {
        // Speichert die Entity direkt und gibt das Form-Layout zurück
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