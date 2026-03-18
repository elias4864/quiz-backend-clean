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

        // Kopie erstellen, um Immutable-Fehler bei shuffle() zu vermeiden
        List<String> allAnswers = new ArrayList<>();
        if (entity.getIncorrectAnswers() != null) {
            allAnswers.addAll(entity.getIncorrectAnswers());
        }
        allAnswers.add(entity.getCorrectAnswer());

        // Antworten mischen
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
     * Entity -> QuestionFormDTO (Admin-Bereich: Antworten getrennt)
     */


    /**
     * QuestionDTO -> Entity
     * WICHTIG: Die ID wird hier mitgenommen, falls vorhanden (für Updates)!
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

        // ID setzen, falls vorhanden (entscheidend für Service.updateQuestion)
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


}