package com.wiss.quizbackend.service;

import com.wiss.quizbackend.entity.GameSession;
import com.wiss.quizbackend.repository.AppUserRepository;
import com.wiss.quizbackend.repository.GameSessionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameService {

    private final GameSessionRepository gameSessionRepository;
    private final AppUserRepository userrepo;

    public GameService(GameSessionRepository gameSessionRepository, AppUserRepository userrepo) {
        this.gameSessionRepository = gameSessionRepository;
        this.userrepo = userrepo;
    }


        //ACID Prinzip mit ko Datenkonsistenz
    @Transactional
    public GameSession startGame(Long userId, String category,
                                 int correctAnswers, int  totalQuestions,  int totalScore, LocalDateTime playedAt) {

        System.out.println("🎮 Game wird gestartet...");

        // Schritt 1: GameSession erstellen und speichern
        int score = correctAnswers * 10; // 10 Punkte pro richtige Antwort

        GameSession session = new GameSession();
        session.setUserId(userId);
        session.setCategory(category);
        session.setTotalQuestions(totalQuestions);
        session.setCorrectAnswers(0);
        session.setTotalScore(0);
        session.setPlayedAt(LocalDateTime.now());
        GameSession saved = gameSessionRepository.save(session);
        System.out.println("✅ 1.: GameSession mit ID: " + saved);

        // Ungültige ruser Id wird simuliert  mit RuntieExpetion
        if (userId == 999L) {
            System.out.println("💥 FEHLER: Ungültiger User!");
            throw new RuntimeException("Invalid user ID: " + userId);
        }

        System.out.println("✅ 2.: Validation OK");

        return saved;
    }

    @Transactional(readOnly = true)
    public List<GameSession> getAllGames() {
        return gameSessionRepository.findAll();
    }

    public GameSession getGameById(Integer id) {
        return gameSessionRepository.findById(id).orElseThrow();
    }




}
