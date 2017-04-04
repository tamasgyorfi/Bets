package hu.bets.service;

public class BetSaveException extends RuntimeException {

    public BetSaveException(String reason) {
        super(reason);
    }

    public BetSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public BetSaveException(Throwable cause) {
        super(cause);
    }
}
