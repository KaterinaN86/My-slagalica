package com.comtrade.service.slagalicaservice;

import com.comtrade.model.slagalicamodel.LettersResponse;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;

import java.security.Principal;

public interface SlagalicaService {

    String lettersForFindingTheWord();
    Integer userWordProcessing(SlagalicaUserWordSubmit slagalicaUserWordSubmit,Principal principal);
    LettersResponse saveLetterForFindingWords(Principal principal);
    String computersLongestWord(String lettersForWord);


}
