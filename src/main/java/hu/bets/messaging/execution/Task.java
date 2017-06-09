package hu.bets.messaging.execution;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.Callable;

public abstract class Task<T> implements Callable<List<String>> {

    private static final Logger LOGGER = Logger.getLogger(Task.class);

    @Override
    public List<String> call() {
        T result = doWork();
        LOGGER.info("Calculated result is: " + result);
        List<String> payload = convertToPayload(result);
        LOGGER.info("Resulting payload is: " + payload);

        return payload;
    }

    protected abstract List<String> convertToPayload(T result);

    protected abstract T doWork();
}
