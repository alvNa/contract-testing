package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.OfferDto;
import org.example.dto.PriceDto;
import org.example.repository.PricingRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PricingService {
    private final PricingRepository pricingRepository;
    private final OfferService offerService;

    public List<PriceDto> findAll() {
        var pricings = pricingRepository.findAll();
        var offersMap = offerService.find().stream()
                .collect(Collectors.toMap(OfferDto::getProductId, Function.identity()));

        return combinePricingAndOffers(pricings, offersMap);
    }

    public PriceDto find(Long productId) {
        var maybePricing = pricingRepository.find(productId);
        var maybeOffer = offerService.find().stream()
                .filter(o -> productId.equals(o.getProductId()))
                .findFirst();

        return combinePriceAndOffer(maybePricing, maybeOffer);
    }

    @NotNull
    private static List<PriceDto> combinePricingAndOffers(List<PriceDto> pricings, Map<Long, OfferDto> offersMap) {
        return pricings.stream()
                .peek(p -> {
                    if (offersMap.containsKey(p.getProductId())) {
                        var offer = offersMap.get(p.getProductId());
                        var currentPrice = p.getPrice();
                        var discount = BigDecimal.valueOf((double) offer.getPercentage() / 100);
                        var newPrice = currentPrice.subtract(currentPrice.multiply(discount));
                        //p.setPrice(newPrice);
                    }
                })
                .collect(Collectors.toList());
    }

    private static PriceDto combinePriceAndOffer(Optional<PriceDto> maybePrice, Optional<OfferDto> maybeOffer) {
        if (maybePrice.isPresent()){
            var price = maybePrice.get();
            if(maybeOffer.isPresent()){
                var offer = maybeOffer.get();
                var currentPrice = price.getPrice();
                var discount = BigDecimal.valueOf((double) offer.getPercentage() / 100);
                var newPrice = currentPrice.subtract(currentPrice.multiply(discount));

                price.setPrice(newPrice);
            }
            return price;
        }
        else return null;
    }
}
