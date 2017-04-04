package hu.bets.service;

import java.util.UUID;

public class IdService {

    public String generateBetId(String userId) {
        return userId + ":" + UUID.randomUUID();
    }

}
