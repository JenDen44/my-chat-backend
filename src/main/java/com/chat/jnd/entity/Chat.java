package com.chat.jnd.entity;

import com.chat.jnd.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chats")
public class Chat {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Convert(converter = StringListConverter.class)
    @Column(length = 700)
    List<String> tokens = new ArrayList<>();

    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    List<Message> messages;
}
