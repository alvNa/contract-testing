package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ProductDto;
import org.example.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.example.controller.InventoryController.INVENTORY_PATH;



@RequiredArgsConstructor
@RestController
@RequestMapping(INVENTORY_PATH)
@Validated
public class InventoryController {

    public static final String INVENTORY_PATH = "/inventory";
    public static final String PRODUCT_QUERY_PARAM = "productId";

    @Autowired
    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> find(@RequestParam(value = PRODUCT_QUERY_PARAM, required = false) String productIdParam) {
        var productId= (nonNull(productIdParam) ? Long.getLong(productIdParam) : null);
        var prices = inventoryService.find(productId);
        return ResponseEntity.ok(prices);
    }
}

