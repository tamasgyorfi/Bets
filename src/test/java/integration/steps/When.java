package integration.steps;

import com.rabbitmq.client.Channel;
import hu.bets.messaging.MessagingConstants;
import integration.steps.util.ApplicationContextHolder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class When {

    public static void iRepeateAPostRequest(String uri, String payload, int nrOfTimes) throws Exception {
        for (int i=0;i<nrOfTimes;i++) {
            iMakeAPostRequest(uri, payload);
        }
    }

    public static HttpResponse iMakeAPostRequest(String uri, String payload) throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(uri);
        HttpEntity entity = new StringEntity(payload);

        postRequest.setEntity(entity);
        postRequest.addHeader("Content-Type", "application/json");
        return client.execute(postRequest);
    }

    public static HttpResponse iMakeAGetRequest(String uri) throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(uri);

        return client.execute(getRequest);
    }

    public static void iSendAMessage(String payload) throws Exception {
        Channel channel = ApplicationContextHolder.getBean(Channel.class);
        channel.queueDeclare(MessagingConstants.AGGREGATE_REPLY_QUEUE_NAME, true, false, false, null);

        channel.basicPublish(MessagingConstants.EXCHANGE_NAME,
                MessagingConstants.AGGREGATE_REQUEST_ROUTING_KEY,
                null,
                payload.getBytes());
    }
}
