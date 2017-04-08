package hu.bets.service;

import java.util.UUID;

public class UuidIdGenerator implements IdGenerator {

    @Override
    public String generateBetId(String userId) {
        return userId + ":" + UUID.randomUUID();
    }

}
