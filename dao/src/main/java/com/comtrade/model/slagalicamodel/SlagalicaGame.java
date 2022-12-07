package com.comtrade.model.slagalicamodel;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "slagalica")
public class SlagalicaGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lettersForFindingTheWord;
    private String computerLongestWord;
    private Integer numOfPoints;
    private Boolean isActive;
}
