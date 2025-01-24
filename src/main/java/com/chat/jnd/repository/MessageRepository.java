package com.chat.jnd.repository;

import com.chat.jnd.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query(value =
            """
            WITH current_chat AS
            (
            SELECT chat_id FROM messages WHERE sender_token = :token
            LIMIT 1
            )
            SELECT msg.* FROM messages msg
            JOIN current_chat chat ON chat.chat_id = msg.chat_id
            ORDER BY id DESC
            LIMIT :limit
            """,
            nativeQuery = true)
    List<Message> findMessagesByToken(@Param("token") String token,
                                      @Param("limit") Integer limit);

    @Query(value =
            """
            WITH current_chat AS
            (
            SELECT chat_id FROM messages WHERE sender_token = :token
            LIMIT 1
            )
            SELECT msg.* FROM messages msg
            JOIN current_chat chat ON chat.chat_id = msg.chat_id
            AND id < :lastId
            ORDER BY id DESC
            LIMIT :limit
            """,
            nativeQuery = true)
    List<Message> findMessagesByParams(@Param("token") String token,
                                       @Param("limit") Integer limit,
                                       @Param("lastId") Integer lastId);
}
