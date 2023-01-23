package com.comtrade.service.slagalicaservice;

import com.comtrade.exceptions.GameNotFoundException;
import com.comtrade.model.slagalicamodel.LettersResponse;
import com.comtrade.model.slagalicamodel.SlagalicaGame;
import com.comtrade.model.slagalicamodel.SlagalicaUserWordSubmit;
import com.comtrade.model.slagalicamodel.SubmitResponse;
import com.comtrade.responses.Response;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface SlagalicaService {

    String lettersForFindingTheWord();
    SubmitResponse userWordProcessing(SlagalicaUserWordSubmit slagalicaUserWordSubmit, Principal principal) throws GameNotFoundException;
    LettersResponse saveLetterForFindingWords(Principal principal) throws GameNotFoundException;
    String computersLongestWord(String lettersForWord);

    ResponseEntity<Response> finishGame(Principal principal) throws GameNotFoundException ;
    boolean isActiveGame(Principal principal) throws GameNotFoundException ;
    LettersResponse getInitData(Principal principal) throws GameNotFoundException;
}
