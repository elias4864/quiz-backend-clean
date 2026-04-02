package com.wiss.quizbackend.controller;

import com.wiss.quizbackend.entity.GameSession;
import com.wiss.quizbackend.service.GameService;
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
    public GameSession startGame(@RequestParam Long userId,
                                 @RequestParam String category,
                                 @RequestParam int correct,
                                 @RequestParam int total) {
        return gameService.startGame(userId, category, correct, total);
    }




}
