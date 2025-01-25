package com.chat.jnd.repository;

import com.chat.jnd.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Query("SELECT c FROM Chat c WHERE c.tokens LIKE CONCAT('%',:token,'%')")
    Optional<Chat> findByToken(@Param("token") String token);
}
