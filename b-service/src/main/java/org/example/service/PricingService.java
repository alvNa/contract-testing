package org.example.service;

import org.example.dto.PriceDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PricingService {
    private List<PriceDto> products = new ArrayList<>();

    public PricingService(){
        var product1 = PriceDto.builder()
                .productId(100L)
                .price(BigDecimal.valueOf(2))
                .build();
        var product2 = PriceDto.builder()
                .productId(101L)
                .price(BigDecimal.valueOf(3))
                .build();
        products.add(product1);
        products.add(product2);
    }

    public List<PriceDto> find(Long productId) {
        return products.stream()
                //.filter(productDto -> Objects.equals(productId, productDto.getProductId()))
                .collect(Collectors.toList());
    }
}
