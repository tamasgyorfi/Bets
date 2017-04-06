package hu.bets.service;

import java.util.UUID;

public class IdGenerator {

    public String generateBetId(String userId) {
        return userId + ":" + UUID.randomUUID();
    }

}
