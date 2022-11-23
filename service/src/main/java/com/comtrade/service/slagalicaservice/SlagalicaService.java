package com.comtrade.service.slagalicaservice;

import com.comtrade.model.slagalicamodel.Slagalica;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;

public interface SlagalicaService {

    String lettersForFindingTheWord();
    Integer userWordProcessing(SlagalicaUserWordSubmit slagalicaUserWordSubmit);
    Slagalica saveLetterForFindingWords();

}
