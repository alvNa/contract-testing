package org.example.scc;

import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.nio.file.Files;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

public class ContractVerifierTest extends BaseTestClass {

    @Value("classpath:data/a-service-response.json")
    private Resource aServiceBodyResourceFile;

    @Test
    public void validateShouldReturnProduct() throws Exception {
        // given:
        MockMvcRequestSpecification request = given();

        // when:
        ResponseOptions response = given().spec(request)
                //.queryParam("number","2")
                .get("/products/100");

        // then:
        Assertions.assertEquals(200, response.statusCode());
        // and:
        var jsonPath = response.getBody().jsonPath();

        Assertions.assertEquals(100,jsonPath.getInt("productId"));
        Assertions.assertEquals("Tomatoes",jsonPath.getString("desc"));
        Assertions.assertEquals(2,jsonPath.getInt("price"));
    }

    @SneakyThrows
    private String getBodyResponse(){
        return Files.readString(aServiceBodyResourceFile.getFile().toPath());
    }
}