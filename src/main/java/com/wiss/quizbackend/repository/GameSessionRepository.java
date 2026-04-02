package com.wiss.quizbackend.repository;

import com.wiss.quizbackend.entity.GameSession;
import org.hibernate.boot.model.convert.spi.JpaAttributeConverterCreationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Integer> {


    List<GameSession> findByUserId(Long userId);

    /**
     * Findet alle GameSessions einer bestimmten Kategorie
     * <p>
     * SQL: SELECT * FROM game_sessions WHERE category = ?
     */
    List<GameSession> findByCategory(String category);

    /**
     * Findet alle GameSessions eines Users, sortiert nach Datum (neueste zuerst)
     * <p>
     * SQL: SELECT * FROM game_sessions WHERE user_id = ? ORDER BY played_at DESC
     */
    List<GameSession> findByUserIdOrderByPlayedAtDesc(Long userId);

    /**
     * Findet alle GameSessions eines Users in einer bestimmten Kategorie
     * <p>
     * SQL: SELECT * FROM game_sessions WHERE user_id = ? AND category = ?
     */
    List<GameSession> findByUserIdAndCategory(Long userId, String category);

}