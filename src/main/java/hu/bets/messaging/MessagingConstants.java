package hu.bets.messaging;

public class MessagingConstants {

    public static  final String EXCHANGE_NAME = "amq.direct";

    public static final String AGGREGATE_REQUEST_QUEUE_NAME = "BETS_REQUEST";

    public static final String AGGREGATE_REQUEST_ROUTING_KEY = "bets.request";
    public static final String AGGREGATE_RESPONSE_ROUTING_KEY = "bets.reply";

}
