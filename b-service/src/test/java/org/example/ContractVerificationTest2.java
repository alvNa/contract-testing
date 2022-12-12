package org.example;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.service.PricingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@Provider("myAwesomeService")
@PactBroker()
@Slf4j
class ContractVerificationTest2 {

    @Autowired
    private MockMvc mockMvc;

    //@InjectMocks
    //private AwesomeController awesomeController = new AwesomeController();

    @Mock
    private PricingService pricingService;

    @BeforeEach
    void beforeEach(PactVerificationContext context) {
        context.setTarget(new MockMvcTestTarget(mockMvc));
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        val consumer = context.getConsumerName();
        log.info("consumer {} is verificating the contract", consumer);
        context.verifyInteraction();
    }
}