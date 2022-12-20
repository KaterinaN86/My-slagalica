package com.comtrade.service;

import com.comtrade.interfaces.MultiPlayerService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MultiPlayerServiceImpl implements MultiPlayerService {

    @Getter
    private final List<Principal> playerQueue = new ArrayList<>();

    public void addPlayerToQueue(Principal principal) {

        if (!playerQueue.contains(principal)) {
            playerQueue.add(principal);
        }

    }

    public void removePlayerFromQueue(Principal principal) {

        if (playerQueue.contains(principal)) {
            playerQueue.remove(principal);
        }
        
    }

}
