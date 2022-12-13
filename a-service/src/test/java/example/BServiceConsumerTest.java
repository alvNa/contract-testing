package example;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.commons.collections4.MapUtils;
import org.example.config.WebFluxConfig;
import org.example.dto.PriceDto;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith({PactConsumerTestExt.class, SpringExtension.class})
@PactTestFor(providerName = BServiceConsumerTest.PROVIDER_NAME)
@Import(WebFluxConfig.class)
public class BServiceConsumerTest {

    public static final String CONSUMER_NAME = "a-service";
    public static final String PROVIDER_NAME = "b-service";
    public static final String PRICES_PATH = "/prices";
    public static final String STATE_1 = "get-price";
    private Long productId = 100L;

    public static final String GET_PRICE_PATH = "/prices/100";

    @Value("classpath:data/b-service-response.json")
    private Resource bServiceBodyResourceFile;

    private Map<String, String> headers = MapUtils.putAll(new HashMap<>(), new String[] {
            "Content-Type", MediaType.APPLICATION_JSON_VALUE
    });

    @Pact(provider=PROVIDER_NAME, consumer=CONSUMER_NAME)
    public RequestResponsePact createContract(PactDslWithProvider builder) {
        JSONObject json = new JSONObject();
        json.put("productId",productId);
        json.put("price", 3);

        return builder
                .given(STATE_1)
                .uponReceiving("Get " + PRICES_PATH + " Request")
                .path(GET_PRICE_PATH)
                .method(HttpMethod.GET.name())
                //.headers(headers)
                .willRespondWith()
                .status(200)
                .body(getBodyResponse())
                .toPact();
    }

    @Test
    @PactTestFor
    void getRequestOK(MockServer mockServer) {
        // when
        ResponseEntity<String> response = new RestTemplate().getForEntity(mockServer.getUrl() + GET_PRICE_PATH, String.class);
        var priceDto = PriceDto.builder()
                .productId(100L)
                .price(BigDecimal.valueOf(3))
                .build();

        var bodyRes = new Gson().toJson(Collections.singletonList(priceDto));

        // then
        assertEquals(response.getStatusCode(),OK);
        assertEquals(response.getBody(), bodyRes);
    }

    @SneakyThrows
    private String getBodyResponse(){
        return Files.readString(bServiceBodyResourceFile.getFile().toPath());
    }
}
