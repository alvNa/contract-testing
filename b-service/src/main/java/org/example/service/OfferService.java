package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.example.dto.OfferDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OfferService {
    public static final String OFFERS_PATH = "/offers";
    private final WebClient webClient;

    public List<OfferDto> find()  {

        val offersResult = webClient.get()
                .uri(OFFERS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                //.body(Mono.just(body), PriceDto.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OfferDto>>(){});

        List<OfferDto> offers = offersResult.block();

        return offers;
    }

}