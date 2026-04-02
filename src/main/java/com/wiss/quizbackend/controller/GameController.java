package com.wiss.quizbackend.controller;

import com.wiss.quizbackend.entity.GameSession;
import com.wiss.quizbackend.service.GameService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/game")
@CrossOrigin("origins = http://localhost:5174")
public class GameController {

    private final GameService gameService;
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }



    @PostMapping("/start")
    @ResponseStatus(HttpStatus.CREATED)
    public GameSession startGame(
            @RequestParam Long userId,

            @RequestParam String category,
            @RequestParam int correctAnswers,
            @RequestParam int totalQuestions,
            @RequestParam int totalScore,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime playedAt) {

        // Ruft den Service auf und übergibt ALLE Daten
        return gameService.startGame(userId,  category, correctAnswers, totalQuestions, totalScore, playedAt);
    }


    /**
     * PUT /api/game/{sessionId}/finish
     * Beendet ein Game mit Resultat
     */

    /**
     * GET /api/game/{sessionId}
     * Lädt eine bestimmte GameSession
     */

}
