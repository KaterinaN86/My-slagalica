package com.comtrade.service.slagalicaservice;

import com.comtrade.model.slagalicamodel.LettersResponse;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;
import com.comtrade.model.slagalicamodel.SubmitResponse;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface SlagalicaService {

    String lettersForFindingTheWord();
    SubmitResponse userWordProcessing(SlagalicaUserWordSubmit slagalicaUserWordSubmit, Principal principal) throws Exception;
    LettersResponse saveLetterForFindingWords(Principal principal);
    String computersLongestWord(String lettersForWord);

    ResponseEntity finishGame(Principal principal) throws Exception;
    boolean isActiveGame(Principal principal) throws Exception;
}
