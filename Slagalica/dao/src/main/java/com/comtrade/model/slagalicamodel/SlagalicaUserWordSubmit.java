package com.comtrade.model.slagalicamodel;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlagalicaUserWordSubmit {

    private Long gameId;
    private String userWord;
    private String lettersForFindingTheWord;
}
