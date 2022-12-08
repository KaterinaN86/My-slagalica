package com.comtrade.model.koznaznamodel;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionContent;

    @ElementCollection
    private List<String> options;

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    private List<KoZnaZnaGame> games;

    private Integer correctAnswer;

}
