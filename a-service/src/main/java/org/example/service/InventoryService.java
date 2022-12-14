package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.PriceDto;
import org.example.dto.ProductDto;
import org.example.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final PricingService pricingService;

    public ProductDto find(Long productId) {
        var maybeProduct = inventoryRepository.find(productId);
        var maybePrice = pricingService.find().stream()
                .filter(p -> productId.equals(p.getProductId()))
                .findFirst();

        return combineProductAndPrice(maybeProduct, maybePrice);
    }

    public List<ProductDto> findAll() {
        var products = inventoryRepository.findAll();
        var pricesMap = pricingService.find().stream()
                .collect(Collectors.toMap(PriceDto::getProductId, Function.identity()));
        return combineProductAndPrices(products, pricesMap);
    }

    private static List<ProductDto> combineProductAndPrices(List<ProductDto> products, Map<Long, PriceDto> pricesMap) {
        return products.stream()
                .peek(p -> {
                    if (pricesMap.containsKey(p.getProductId())) {
                        p.setPrice(pricesMap.get(p.getProductId()).getPrice());
                    }
                })
                .collect(Collectors.toList());
    }

    private static ProductDto combineProductAndPrice(Optional<ProductDto> maybeProduct, Optional<PriceDto> maybePrice) {
        if (maybeProduct.isPresent()){
            var product = maybeProduct.get();
            if(maybePrice.isPresent()){
                var price = maybePrice.get().getPrice();
                product.setPrice(price);
            }
            return product;
        }
        else return null;
    }
}
