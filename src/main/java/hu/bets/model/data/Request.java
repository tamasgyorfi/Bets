package hu.bets.model.data;

import java.util.ArrayList;
import java.util.List;

public class Request {

    public enum RequestType {
        BETS_REQUEST,
        ACKNOWLEDGE_REQUEST
    }

    private List<String> payload = new ArrayList<>(50);
    private RequestType type;

    private Request() {

    }

    public Request(List<String> payload, RequestType type) {
        this.payload = payload;
        this.type = type;
    }

    public List<String> getPayload() {
        return payload;
    }

    public RequestType getType() {
        return type;
    }
}
