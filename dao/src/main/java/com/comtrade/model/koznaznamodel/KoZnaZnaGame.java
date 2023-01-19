package com.comtrade.model.koznaznamodel;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KoZnaZnaGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Question> questions;

    private int indexOfTheCurrentQuestionPlayerOne;
    private int indexOfTheCurrentQuestionPlayerTwo;
}
