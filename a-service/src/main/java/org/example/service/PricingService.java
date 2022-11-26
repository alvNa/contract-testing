package org.example.service;


import lombok.RequiredArgsConstructor;
import lombok.val;
import org.example.dto.PriceDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PricingService {
    public static final String PRICES_PATH = "/prices";
    private final WebClient webClient;

    public List<PriceDto> find()  {

        val pricesResult = webClient.get()
                .uri(PRICES_PATH)
                .accept(MediaType.APPLICATION_JSON)
                //.body(Mono.just(body), PriceDto.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PriceDto>>(){});

        List<PriceDto> prices = pricesResult.block();

        return prices;
    }

}