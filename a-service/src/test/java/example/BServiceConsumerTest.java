package example;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.example.dto.PriceDto;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = BServiceConsumerTest.PROVIDER_NAME)
public class BServiceConsumerTest {

    public static final String CONSUMER_NAME = "a-service";
    public static final String PROVIDER_NAME = "b-service";
    public static final String PRICES_PATH = "/prices";
    public static final String STATE_1 = "get-price";
    private Long productId = 100L;

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
                .pathFromProviderState(PRICES_PATH+"/${productId}", PRICES_PATH+"/"+productId)
                //.matchPath(format(PRICES_PATH +"/%s", productId))
                //.path(PRICES_PATH + "/" + productId)
                .method(HttpMethod.GET.name())
                .headers(headers)
                .willRespondWith()
                .status(200)
                .body(json)
                .body(PactDslJsonArray.arrayEachLike()
                        .numberType("productId", 100)
                        .numberType("price", 3)
                        .closeObject()
                )
                //.body("[{\"productId\": 100, \"price\": 3 }]")
                .toPact();
    }

    @Test
    @PactTestFor
    void getRequestOK(MockServer mockServer) {
        // when
        //var url = mockServer.getUrl() + PRICES_PATH + "/" + productId;
        ResponseEntity<String> response = new RestTemplate().getForEntity(mockServer.getUrl() + PRICES_PATH + "/{productId}", String.class, productId);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getHeaders().get("Content-Type").contains(MediaType.APPLICATION_JSON_VALUE)).isTrue();
        assertThat(response.getBody()).contains("productId","100","price","3");
    }

    /*@Test
    @PactTestFor(pactMethod = "createContract")
    @DisplayName("Get /prices OK")
    void getRequestOK2(MockServer mockServer) throws IOException {

        BasicHttpEntity bodyRequest = new BasicHttpEntity();
        bodyRequest.setContent(IOUtils.toInputStream(echoRequest, Charset.defaultCharset()));

        expectedResult = new PriceDto();
        expectedResult.setPhrase("hello! sent at: 1593373353 worked at: 1593373360");

        HttpResponse httpResponse = Request.Post(mockServer.getUrl() + API_ECHO)
                .body(bodyRequest)
                .execute()
                .returnResponse();

        ObjectMapper objectMapper = new ObjectMapper();
        EchoResponse actualResult = objectMapper.readValue(httpResponse.getEntity().getContent(), EchoResponse.class);

        assertEquals(expectedResult, actualResult);
    }*/
}
