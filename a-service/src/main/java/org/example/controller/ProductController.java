package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ProductDto;
import org.example.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
@Validated
public class ProductController {
    public static final String PRODUCTS_PATH = "/products";
    public static final String PRODUCT_PATH = "/products/{productId}";

    @Autowired
    private final InventoryService inventoryService;

    @GetMapping(PRODUCTS_PATH)
    public ResponseEntity<List<ProductDto>> findAll() {
        var products = inventoryService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping(PRODUCT_PATH)
    public ResponseEntity<ProductDto> find(@PathVariable Long productId) {
        var product = inventoryService.find(productId);
        return ResponseEntity.ok(product);
    }
}

