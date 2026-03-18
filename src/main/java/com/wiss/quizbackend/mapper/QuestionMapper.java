package com.wiss.quizbackend.mapper;

import com.wiss.quizbackend.dto.QuestionDTO;
import com.wiss.quizbackend.dto.QuestionFormDTO;
import com.wiss.quizbackend.entity.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Optimierter Mapper für die Konvertierung zwischen Question Entity und DTOs.
 */
public class QuestionMapper {

    /**
     * Entity -> QuestionDTO (Spiellogik: Antworten gemischt)
     */
    public static QuestionDTO toDTO(Question entity) {
        if (entity == null) return null;

        List<String> allAnswers = new ArrayList<>();
        if (entity.getIncorrectAnswers() != null) {
            allAnswers.addAll(entity.getIncorrectAnswers());
        }
        allAnswers.add(entity.getCorrectAnswer());

        // Antworten für den Spieler mischen
        Collections.shuffle(allAnswers);

        return new QuestionDTO(
                entity.getId(),
                entity.getQuestion(),
                entity.getCorrectAnswer(),
                allAnswers,
                entity.getCategory(),
                entity.getDifficulty()
        );
    }

    /**
     * Entity -> QuestionFormDTO (Admin-Bereich: Antworten getrennt für das Formular)
     * NEU: Hinzugefügt für die Übereinstimmung mit dem Service
     */
    public static QuestionFormDTO toFormDTO(Question entity) {
        if (entity == null) return null;
        return new QuestionFormDTO(
                entity.getId(),
                entity.getQuestion(),
                entity.getCorrectAnswer(),
                entity.getIncorrectAnswers(),
                entity.getCategory(),
                entity.getDifficulty() // ← Komma entfernt
        ); // ← Semikolon hinzugefügt
    }
    /**
     * QuestionDTO -> Entity
     */
    public static Question toEntity(QuestionDTO dto) {
        if (dto == null) return null;

        List<String> incorrectAnswers = dto.getAnswers() == null ? new ArrayList<>() :
                dto.getAnswers().stream()
                        .filter(answer -> !answer.equals(dto.getCorrectAnswer()))
                        .toList();

        Question entity = new Question(
                dto.getQuestion(),
                dto.getCorrectAnswer(),
                incorrectAnswers,
                dto.getCategory(),
                dto.getDifficulty()
        );

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }

        return entity;
    }

    // --- Batch-Konvertierungen ---

    public static List<QuestionDTO> toDTOList(List<Question> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(QuestionMapper::toDTO).toList();
    }

    /**
     * NEU: Hinzugefügt für die Übereinstimmung mit dem Service
     */
    public static List<QuestionFormDTO> toFormDTOList(List<Question> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(QuestionMapper::toFormDTO).toList();
    }
}