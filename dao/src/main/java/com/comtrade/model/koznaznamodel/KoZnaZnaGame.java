package com.comtrade.model.koznaznamodel;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class KoZnaZnaGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Question> questions;

    private int numberOfPoints;

    private boolean isActiveGame;

    private int indexOfTheCurrentQuestion;
}
