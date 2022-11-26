package org.example.repository;

import org.example.dto.PriceDto;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PricingRepository {
    private List<PriceDto> pricings = new ArrayList<>();

    public PricingRepository(){
        var product1 = PriceDto.builder()
                .productId(100L)
                .price(BigDecimal.valueOf(2))
                .build();
        var product2 = PriceDto.builder()
                .productId(101L)
                .price(BigDecimal.valueOf(3))
                .build();
        pricings.add(product1);
        pricings.add(product2);
    }

    public List<PriceDto> find(Long productId) {
        return pricings.stream()
                //.filter(productDto -> Objects.equals(productId, productDto.getProductId()))
                .collect(Collectors.toList());
    }
}
