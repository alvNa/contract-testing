//package org.example.scc;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import static org.example.controller.ProductController.PRODUCTS_PATH;
//import static org.example.service.PricingService.PRICES_PATH;
//
//@RestController
//public class FakeProductsController {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @GetMapping(PRODUCTS_PATH)
//    public String checkOddAndEven() {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-Type", "application/json");
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                "http://localhost:8090" + PRICES_PATH,
//                HttpMethod.GET,
//                new HttpEntity<>(httpHeaders),
//                String.class);
//
//        return responseEntity.getBody();
//    }
//}
