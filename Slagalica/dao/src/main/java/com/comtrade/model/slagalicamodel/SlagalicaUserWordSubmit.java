package com.comtrade.model.slagalicamodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlagalicaUserWordSubmit {

    private Long gameId;
    private String userWord;
    private String lettersForFindingTheWord;
}
