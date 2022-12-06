package com.comtrade.service.slagalicaservice;

import com.comtrade.model.slagalicamodel.SlagalicaGame;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;

public interface SlagalicaService {

    String lettersForFindingTheWord();
    Integer userWordProcessing(SlagalicaUserWordSubmit slagalicaUserWordSubmit);
    SlagalicaGame saveLetterForFindingWords();
    String computersLongestWord(String lettersForWord);


}
