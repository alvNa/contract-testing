package org.example.service;

import org.example.dto.OfferDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfferService {
    private List<OfferDto> offers = new ArrayList<>();

    public OfferService(){
        var offer1 = OfferDto.builder()
                .offerId(1L)
                .productId(100L)
                .from(LocalDate.of(2022,11,20))
                .to(LocalDate.of(2022,11,28))
                .promotionDesc("Black week")
                .percentage(30)
                .build();
        var offer2 = OfferDto.builder()
                .offerId(1L)
                .productId(101L)
                .from(LocalDate.of(2022,12,01))
                .to(LocalDate.of(2022,12,31))
                .promotionDesc("Christians discount")
                .percentage(15)
                .build();
        offers.add(offer1);
        offers.add(offer2);
    }

    public List<OfferDto> find() {
        return offers;
    }

    public void save(OfferDto offerDto) {
        offers.add(offerDto);
    }

}
