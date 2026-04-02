package com.wiss.quizbackend.repository;

import com.wiss.quizbackend.dto.LeaderboardDTO;
import com.wiss.quizbackend.entity.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LeaderboardRepository extends JpaRepository<GameSession,Long> {


    /**
     * Top 10 Spieler (global)
     *
     * Gruppiert alle GameSessions nach User und summiert den Score.
     *
     * @param pageable Für LIMIT (PageRequest.of(0, 10))
     * @return Array: [userId, totalScore, gamesPlayed]
     */
    @Query("""
        SELECT g.userId, SUM(g.totalScore) as totalScore, COUNT(g.id) as gamesPlayed
        FROM GameSession g
        GROUP BY g.userId
        ORDER BY totalScore DESC
        """)
    List<Object[]> findTop10Players(Pageable pageable);


    @Query("""
        SELECT g.userId, SUM(g.totalScore) as totalScore, COUNT(g.id) as gamesPlayed
        FROM GameSession g
        WHERE g.category = :category
        GROUP BY g.userId
        ORDER BY totalScore DESC
        """)
    List<Object[]> findTop10PlayersByCategory(
            @Param("category") String category,
            Pageable pageable
    );



    @Query("SELECT COUNT(g) FROM GameSession g WHERE g.userId = :userId")
    Long countGamesByUser(@Param("userId") Long userId);


    @Query("SELECT SUM(g.totalScore) FROM GameSession g WHERE g.userId = :userId")
    Integer sumScoreByUser(@Param("userId") Long userId);


    @Query("SELECT AVG(g.totalScore) FROM GameSession g WHERE g.userId = :userId")
    Double averageScoreByUser(@Param("userId") Long userId);

    @Query("SELECT MAX(g.totalScore)FROM GameSession g WHERE g.userId = :userId")
    Integer maxScoreByUser(@Param("userId") Long userId);




    @Query("""
        SELECT g.category, COUNT(g.id) as count
        FROM GameSession g
        GROUP BY g.category
        ORDER BY count DESC
        """)
    List<Object[]> countGamesByCategory();





}
