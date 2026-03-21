package com.wiss.quizbackend.controller;

import com.wiss.quizbackend.dto.RegisterRequestDTO;
import com.wiss.quizbackend.dto.RegisterResponseDTO;
import com.wiss.quizbackend.entity.AppUser;
import com.wiss.quizbackend.entity.Role;
import com.wiss.quizbackend.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") //
public class AuthController {

    private final AppUserService appUserService;

    //Dependency Injection

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        try {
            // Service Layer aufrufen
            AppUser newUser = appUserService.registerUser(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword(),
                    Role.PLAYER  // Default Role für neue User
            );

            // Im Try-Block des AuthControllers:
              RegisterResponseDTO response = new RegisterResponseDTO(
                     newUser.getId(),
                     newUser.getUsername(),         // 2. String
                     newUser.getEmail(),            // 3. String
                     newUser.getRole().name(),
                     "Registriert");



            // HTTP 200 OK mit Response Body
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // HTTP 400 Bad Request bei Validation Errors
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);

        } catch (Exception e) {
            // HTTP 500 bei unerwarteten Fehlern
            Map<String, String> error = new HashMap<>();
            error.put("error", "Registrierung fehlgeschlagen");

  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }


    /**
     * GET /api/auth/check-username/{username}
     * Prüft, ob ein Username verfügbar ist.
     * Nützlich für Live-Validation im Frontend.
     *
     * Response:
     * {
     *   "available": true/false,
     *   "message": "Username ist verfügbar" /
     *              "Username bereits vergeben"
     * }
     */
    @GetMapping("/check-username/{username}")
    public ResponseEntity<Map<String, Object>> checkUsername(@PathVariable String username) {
        boolean exists = appUserService.findByUsername(username).isPresent();
        Map<String, Object> response = new HashMap<>();
        response.put("available", !exists);
        response.put("message", exists ? "Username bereits vergeben" : "Username ist verfügbar");
        return ResponseEntity.ok(response);
    }
    /**
     * GET /api/auth/check-email/{email}
     *
     * Prüft ob eine Email verfügbar ist.
     *
     * Response:
     * {
     *   "available": true/false,
     *   "message": "Email ist verfügbar" / "Email bereits registriert"
     * }



    /**
     * GET /api/auth/test
     *
     * Simple Test Endpoint.
     * Prüft ob der Controller erreichbar ist.
     */

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Map<String, Object>> checkEmail(@PathVariable String email) {
        // Benutze hier im Service am besten eine Methode existsByEmail
        // Falls du nur findByEmail hast: appUserService.findByEmail(email).isPresent()
        boolean exists = appUserService.existsByEmail(email);

        Map<String, Object> response = new HashMap<>();
        response.put("available", !exists);
        response.put("message", exists ? "Email bereits registriert" : "Email ist verfügbar");

        return ResponseEntity.ok(response);
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth Controller funktioniert!");
    }



}
