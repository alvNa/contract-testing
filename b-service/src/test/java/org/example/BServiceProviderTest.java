package org.example;


import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import org.example.controller.PricingController;
import org.example.dto.PriceDto;
import org.example.repository.PricingRepository;
import org.example.service.PricingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.example.BServiceProviderTest.PROVIDER_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = {PricingController.class})
@Provider(PROVIDER_NAME)
@PactBroker
public class BServiceProviderTest {
    public static final String PROVIDER_NAME = "b-service";
    public static final String STATE_1 = "get-price";
    public static final String STATE_2 = "save-price";

    @MockBean
    private PricingRepository pricingRepository;

    @MockBean
    private PricingService pricingService;

    @Autowired
    private MockMvc mockMvc;

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    public void beforeEach(PactVerificationContext context) {
        context.setTarget(new MockMvcTestTarget(mockMvc));
    }

    @State(STATE_1)
    void getPrice() {
        var price1 = PriceDto.builder()
                .productId(100L)
                .price(BigDecimal.valueOf(2))
                .build();

        when(pricingRepository.find(any())).thenReturn(List.of(price1));
    }
}