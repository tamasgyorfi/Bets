package integration.steps;

import com.google.gson.Gson;
import hu.bets.web.model.BetResponse;
import integration.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import static integration.Constants.HOST;
import static integration.Constants.PORT;

public class When {

    public static CloseableHttpResponse iMakeAPostRequest(String uri, String payload) throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(uri);
        HttpEntity entity = new StringEntity(payload);

        postRequest.setEntity(entity);
        postRequest.addHeader("Content-Type", "application/json");
        return client.execute(postRequest);
    }

}
