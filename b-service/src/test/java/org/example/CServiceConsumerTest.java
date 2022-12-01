package org.example;

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
import static org.example.CServiceConsumerTest.PROVIDER_NAME;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = PROVIDER_NAME, hostInterface="localhost")
public class CServiceConsumerTest {

    public static final String PROVIDER_NAME = "c-service";
    public static final String OFFERS_PATH = "/offers";

    private Map<String, String> headers = MapUtils.putAll(new HashMap<>(), new String[] {
            "Content-Type", MediaType.APPLICATION_JSON_VALUE
    });

    @Pact(provider=PROVIDER_NAME, consumer="b-service")
    public RequestResponsePact createContract(PactDslWithProvider builder) {
        return builder
                .given("test GET")
                .uponReceiving("GET REQUEST")
                .path(OFFERS_PATH)
                .method("GET")
                //.headers(headers)
                .willRespondWith()
                .status(200)
                .body("{\"offerId\": 1, \"productId\": 100 }")
                .toPact();
    }

    @Test
    @PactTestFor
    void getRequestOK(MockServer mockServer) {
        // when
        ResponseEntity<String> response = new RestTemplate().getForEntity(mockServer.getUrl() + OFFERS_PATH, String.class);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        //assertThat(response.getHeaders().get("Content-Type").contains("application/json")).isTrue();
        assertThat(response.getBody()).contains("offerId","1","productId","100");
    }
}
