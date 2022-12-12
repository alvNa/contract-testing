package org.example;


import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.OfferController;
import org.example.dto.OfferDto;
import org.example.service.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.example.CServiceProviderTest.PROVIDER_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = {OfferController.class})
@Provider(PROVIDER_NAME)
@PactBroker
@Slf4j
public class CServiceProviderTest {
    public static final String PROVIDER_NAME = "c-service";
    public static final String STATE_1 = "get-offer";
    public static final String STATE_2 = "save-offer";

    @MockBean
    private OfferService offerService;

    @Autowired
    private MockMvc mockMvc;

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        var consumer = context.getConsumerName();
        log.info("consumer {} is verificating the contract", consumer);
        context.verifyInteraction();
    }

    @BeforeEach
    public void beforeEach(PactVerificationContext context) {
        context.setTarget(new MockMvcTestTarget(mockMvc));
    }

    @State(STATE_1)
    void getOffer() {
        var offer1 = OfferDto.builder()
                .offerId(1L)
                .productId(100L)
                .promotionDesc("Black Friday")
                .build();

        when(offerService.find()).thenReturn(List.of(offer1));
    }

    @State(STATE_2)
    void saveOffer() {
        doNothing().when(offerService).save(any(OfferDto.class));
    }
}