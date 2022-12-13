package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.PriceDto;
import org.example.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.example.controller.PricingController.PRICES_PATH;



@RequiredArgsConstructor
@RestController
//@RequestMapping(PRICES_PATH)
@Validated
public class PricingController {

    public static final String PRICES_PATH = "/prices";
    public static final String PRICE_PATH = "/prices/{productId}";
    public static final String PRODUCT_QUERY_PARAM = "productId";

    @Autowired
    private final PricingService pricingService;

    /*@GetMapping(PRICES_PATH)
    public ResponseEntity<List<PriceDto>> findAll() {
        var prices = pricingService.findAll();
        return ResponseEntity.ok(prices);
    }*/

    @GetMapping(PRICE_PATH)
    public ResponseEntity<List<PriceDto>> find(@PathVariable Long productId) {
        //var productId= (nonNull(productIdParam) ? Long.getLong(productIdParam) : null);
        var prices = pricingService.find(productId);
        return ResponseEntity.ok(prices);
    }
}

