package com.comtrade.model.koznaznamodel;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionContent;

    @ElementCollection
    private List<String> options;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<KoZnaZnaGame> games;

    private Integer correctAnswer;

}
