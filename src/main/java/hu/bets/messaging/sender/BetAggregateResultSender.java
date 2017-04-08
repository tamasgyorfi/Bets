package hu.bets.messaging.sender;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import hu.bets.messaging.MessagingConstants;
import hu.bets.model.data.BetAggregationResponse;
import hu.bets.model.data.UserBet;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BetAggregateResultSender {

    private static final int NR_OF_RETRIES = 3;
    private static final Logger LOGGER = Logger.getLogger(BetAggregateResultSender.class);

    private volatile boolean shouldContinue = true;

    private CompletionService<List<List<UserBet>>> completionService;
    private Channel channel;

    public BetAggregateResultSender(CompletionService<List<List<UserBet>>> completionService, Channel channel) {
        this.completionService = completionService;
        this.channel = channel;
    }


    private void run() {
        while (shouldContinue) {
            Future<List<List<UserBet>>> userBets = completionService.poll();
            try {
                if (userBets != null) {
                    send(userBets.get());
                }
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("Unable to send User Bets.", e);
            }
        }
    }

    public void start() {
        new Thread(() -> run()).start();
        LOGGER.info("Message sender thread started successfully.");
    }

    public void stop() {
        this.shouldContinue = false;
    }

    private void send(List<List<UserBet>> userBets) {
        for (List<UserBet> userBet : userBets) {
            BetAggregationResponse response = new BetAggregationResponse(userBet.size(), userBet, getHash(userBet));
            String payload = new Gson().toJson(response);

            sendBatch(payload);
        }
    }

    private void sendBatch(String payload) {
        for (int i = 0; i < NR_OF_RETRIES; i++) {
            try {
                channel.basicPublish(MessagingConstants.EXCHANGE_NAME, MessagingConstants.AGGREGATE_RESPONSE_ROUTING_KEY, null, payload.getBytes());
                break;
            } catch (IOException e) {
                LOGGER.error("Unable to send batch: " + payload, e);
            }
        }
    }

    private String getHash(List<UserBet> userBet) {
        try {
            String betsString = userBet.toString();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(betsString.getBytes());
            byte[] digest = md5.digest();

            return new BigInteger(1, digest).toString(16);
        } catch (NoSuchAlgorithmException e) {

        }

        return "Error hashing bets.";
    }
}