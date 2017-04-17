package hu.bets.messaging.execution;

import java.util.List;
import java.util.concurrent.Callable;

public abstract class Task<T> implements Callable<List<String>> {

    @Override
    public List<String> call() {
        T result = doWork();
        List<String> payload = convertToPayload(result);

        return payload;
    }

    protected abstract List<String> convertToPayload(T result);

    protected abstract T doWork();
}
