package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.PriceDto;
import org.example.dto.ProductDto;
import org.example.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final PricingService pricingService;

    public List<ProductDto> find(Long productId) {
        var products = inventoryRepository.find(productId);
        var pricesMap = pricingService.find().stream()
                .collect(Collectors.toMap(PriceDto::getProductId, Function.identity()));

        return products.stream()
                .peek(p -> {
                    if (pricesMap.containsKey(p.getProductId())){
                        p.setPrice(pricesMap.get(p.getProductId()).getPrice());
                    }
                })
                .collect(Collectors.toList());
    }
}
