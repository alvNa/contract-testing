package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.OfferDto;
import org.example.dto.PriceDto;
import org.example.repository.PricingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PricingService {
    private final PricingRepository pricingRepository;
    private final OfferService offerService;

    public List<PriceDto> find(Long productId) {
        var pricings = pricingRepository.find(productId);
        var offersMap = offerService.find().stream()
                .collect(Collectors.toMap(OfferDto::getProductId, Function.identity()));

        return pricings.stream()
                .peek(p -> {
                    if (offersMap.containsKey(p.getProductId())){
                        var offer = offersMap.get(p.getProductId());
                        var currentPrice = p.getPrice();
                        var discount = BigDecimal.valueOf((double) offer.getPercentage() / 100);
                        var newPrice = currentPrice.subtract(currentPrice.multiply(discount));
                        p.setPrice(newPrice);
                    }
                })
                .collect(Collectors.toList());
    }
}
