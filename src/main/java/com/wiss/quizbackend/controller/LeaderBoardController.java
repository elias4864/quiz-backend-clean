package com.wiss.quizbackend.controller;

import com.wiss.quizbackend.dto.LeaderboardDTO;
import com.wiss.quizbackend.repository.GameSessionRepository;
import com.wiss.quizbackend.repository.LeaderboardRepository;
import com.wiss.quizbackend.service.LeaderBoardService;
import org.springframework.web.bind.annotation.*;
import com.wiss.quizbackend.dto.LeaderboardDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leaderboard")
@CrossOrigin("origins = http://localhost:5174")
public class LeaderBoardController {
    private  final LeaderBoardService leaderBoardService;

    public LeaderBoardController(LeaderBoardService leaderBoardService) {
        this.leaderBoardService = leaderBoardService;
    }




    @GetMapping("/top10")
    public List<LeaderboardDTO> getTop10Players() {
        return leaderBoardService.getTop10Players();
    }

    /**
     * GET /api/leaderboard/top10/sports
     * Lädt die Top 10 Spieler einer Kategorie
     */
    @GetMapping("/top10/{category}")
    public List<LeaderboardDTO> getTop10ByCategory(@PathVariable String category) {
        return leaderBoardService.getTop10PlayersByCategory(category);
    }

    /**
     * GET /api/leaderboard/user/1/stats
     * Lädt Statistiken eines Users
     */
    @GetMapping("/user/{userId}/stats")
    public Map<String, Object> getUserStats(@PathVariable Long userId) {
        return leaderBoardService.getUserStats(userId);
    }

    /**
     * GET /api/leaderboard/categories
     * Lädt Statistiken zu allen Kategorien
     */
    @GetMapping("/categories")
    public List<Map<String, Object>> getCategoryStats() {
        return leaderBoardService.getCategoryStats();
    }

}


