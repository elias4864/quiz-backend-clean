package com.wiss.quizbackend.repository;

import com.wiss.quizbackend.entity.GameSession;
import org.hibernate.boot.model.convert.spi.JpaAttributeConverterCreationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Integer> {



}
