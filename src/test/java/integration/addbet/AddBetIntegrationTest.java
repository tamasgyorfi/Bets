package integration.addbet;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import hu.bets.config.ApplicationConfig;
import hu.bets.config.WebConfig;
import hu.bets.dbaccess.DataSourceHolder;
import hu.bets.web.model.BetResponse;
import integration.Constants;
import integration.FakeDbConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bson.Document;
import org.eclipse.jetty.server.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Properties;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AddBetIntegrationTest {

    private static final String HOST = "localhost";
    private static final String PORT = "10000";
    private static final String ENDPOINT = "/bets/football/v1/result";
    private static final String PROTOCOL = "http";

    private static ApplicationContext context;

    @BeforeClass
    public static void startup() throws Exception {
        setEnvironment();
        setServer();
    }

    private static void setServer() {
        context = new AnnotationConfigApplicationContext(ApplicationConfig.class, WebConfig.class, FakeDbConfig.class);
        final Server server = context.getBean(Server.class);

        new Thread(() -> {
            try {
                server.start();
                server.join();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }).start();
    }

    private static void setEnvironment() {
        Properties properties = System.getProperties();
        properties.setProperty("HOST", HOST);
        properties.setProperty("PORT", PORT);
    }

    @Test
    public void addBet() throws Exception {
        String betId = assertPostSuccess();
        assertDbInsertSuccess(betId);
    }

    private void assertDbInsertSuccess(String betId) {
        DataSourceHolder dataSourceHolder = context.getBean(DataSourceHolder.class);
        MongoCollection<Document> collection = dataSourceHolder.getCollection();

        assertEquals(1, collection.count());

        BasicDBObject query = new BasicDBObject();
        query.put("betId", betId);

        assertNotNull(collection.find(query).first());
    }

    private String assertPostSuccess() throws Exception {

        CloseableHttpResponse response = makePostRequest();
        BetResponse betResponse = convertResponse(response);

        assertFalse(betResponse.getId().isEmpty());
        assertTrue(betResponse.getError().isEmpty());

        return betResponse.getId();
    }

    private BetResponse convertResponse(HttpResponse response) throws Exception {
        return new Gson().fromJson(EntityUtils.toString(response.getEntity()), BetResponse.class);
    }

    private CloseableHttpResponse makePostRequest() throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(PROTOCOL + "://" + HOST + ":" + PORT + ENDPOINT);
        HttpEntity entity = new StringEntity(Constants.POST_JSON);

        postRequest.setEntity(entity);
        postRequest.addHeader("Content-Type", "application/json");
        return client.execute(postRequest);
    }
}
