import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return a product when the productId is valid"
    request{
        method GET()
        url("/products/100") {
        }
    }
    response {
        body("{\n" +
                "  \"productId\": 100,\n" +
                "  \"desc\": \"Tomatoes\",\n" +
                "  \"price\": 2\n" +
                "}")
        status 200
    }
}