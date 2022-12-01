package example;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = BServiceConsumerTest.PROVIDER_NAME)
public class BServiceConsumerTest {

    public static final String PROVIDER_NAME = "b-service";
    public static final String CONSUMER_NAME = "a-service";
    public static final String PRICES_PATH = "/prices";

    private Map<String, String> headers = MapUtils.putAll(new HashMap<>(), new String[] {
            "Content-Type", MediaType.APPLICATION_JSON_VALUE
    });

    @Pact(provider=PROVIDER_NAME, consumer=CONSUMER_NAME)
    public RequestResponsePact createContract(PactDslWithProvider builder) {
        return builder
                .given("test GET")
                .uponReceiving("GET REQUEST")
                .path(PRICES_PATH)
                .method("GET")
                //.headers(headers)
                .willRespondWith()
                .status(200)
                .body("{\"productId\": 100, \"price\": 3 }")
                .toPact();
    }

    @Test
    @PactTestFor
    void getRequestOK(MockServer mockServer) {
        // when
        ResponseEntity<String> response = new RestTemplate().getForEntity(mockServer.getUrl() + PRICES_PATH, String.class);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        //assertThat(response.getHeaders().get("Content-Type").contains("application/json")).isTrue();
        assertThat(response.getBody()).contains("productId","100","price","3");
    }
}
