package hu.bets.messaging;

public class MessagingConstants {

    public static  final String EXCHANGE_NAME = "amq.direct";

    public static final String BETS_TO_SCORES_QUEUE = "BETS_TO_SCORES_QUEUE";
    public static final String SCORES_TO_BETS_QUEUE = "SCORES_TO_BETS";

    public static final String SCORES_TO_BETS_ROUTE = "in.bets.request";
    public static final String BETS_TO_SCORES_ROUTE = "in.bets.reply";

}
