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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.CServiceConsumerTest.PROVIDER_NAME;

@ExtendWith({PactConsumerTestExt.class, SpringExtension.class})
@PactTestFor(providerName = PROVIDER_NAME)
public class CServiceConsumerTest {

    public static final String PROVIDER_NAME = "c-service";
    public static final String CONSUMER_NAME = "b-service";
    public static final String OFFERS_PATH = "/offers";
    public static final String STATE_1 = "get-offer";

    @Value("classpath:data/c-service-body.json")
    private Resource cServiceBodyResourceFile;

    private Map<String, String> headers = MapUtils.putAll(new HashMap<>(), new String[] {
            "Content-Type", MediaType.APPLICATION_JSON_VALUE
    });


    @Pact(provider=PROVIDER_NAME, consumer=CONSUMER_NAME)
    public RequestResponsePact createContract(PactDslWithProvider builder) throws IOException {
        var body = Files.readString(cServiceBodyResourceFile.getFile().toPath());

        return builder
                .given(STATE_1)
                .uponReceiving("Get " + OFFERS_PATH + " Request")
                .path(OFFERS_PATH)
                .method("GET")
                //.headers(headers)
                .willRespondWith()
                .status(200)
                .body(body)
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
        assertThat(response.getBody()).contains("offerId","1","productId","100","promotionDesc","Black Friday");
    }
}
