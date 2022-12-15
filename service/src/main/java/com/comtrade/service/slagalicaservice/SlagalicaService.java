package com.comtrade.service.slagalicaservice;

import com.comtrade.model.slagalicamodel.LettersResponse;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;
import com.comtrade.model.slagalicamodel.SubmitResponse;

import java.security.Principal;

public interface SlagalicaService {

    String lettersForFindingTheWord();
    SubmitResponse userWordProcessing(SlagalicaUserWordSubmit slagalicaUserWordSubmit, Principal principal) throws Exception;
    LettersResponse saveLetterForFindingWords(Principal principal);
    String computersLongestWord(String lettersForWord);


}
