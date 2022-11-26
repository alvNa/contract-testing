package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.OfferDto;
import org.example.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.example.controller.OfferController.OFFERS_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(OFFERS_PATH)
@Validated
public class OfferController {

    public static final String OFFERS_PATH = "/offers";

    @Autowired
    private final OfferService offerService;

    @GetMapping
    public ResponseEntity<List<OfferDto>> find() {
        var prices = offerService.find();
        return ResponseEntity.ok(prices);
    }
}

