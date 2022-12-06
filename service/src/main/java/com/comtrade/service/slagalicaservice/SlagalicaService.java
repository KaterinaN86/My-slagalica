package com.comtrade.service.slagalicaservice;

import com.comtrade.model.slagalicamodel.Slagalica;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;

import java.util.Map;

public interface SlagalicaService {

    String lettersForFindingTheWord();
    Integer userWordProcessing(SlagalicaUserWordSubmit slagalicaUserWordSubmit);
    Slagalica saveLetterForFindingWords();
    String computersLongestWord(String lettersForWord);


}
