package com.wiss.quizbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.method.configuration
        .EnableMethodSecurity;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration  // Spring scannt diese Klasse beim Start
@EnableWebSecurity
@EnableMethodSecurity // WICHTIG: Erlaubt @PreAuthorize("hasRole('ADMIN')")
public class SecurityConfig {




    @Bean
    public PasswordEncoder passwordEncoder() {
        // Work Factor: 10-12 ist Standard, 14+ für hochsensible Daten
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Security Filter Chain Configuration.
     *
     * TEMPORÄR: Alle Requests erlauben für Entwicklung
     * SPÄTER: JWT Authentication hinzufügen
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF deaktivieren (für REST APIs Standard, macht Tests einfacher)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. H2-Konsole erlauben (falls du sie nutzt)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // 3. Berechtigungen festlegen
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // H2 frei
                        .requestMatchers("/api/questions/**").authenticated() // API geschützt
                        .anyRequest().permitAll()
                )
                // 4. Login-Methoden
                .httpBasic(withDefaults())
                .formLogin(withDefaults());

        return http.build();
    }



}
