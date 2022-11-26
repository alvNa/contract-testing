package org.example.repository;

import org.example.dto.ProductDto;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InventoryRepository {
    private List<ProductDto> products = new ArrayList<>();

    public InventoryRepository(){
        var product1 = ProductDto.builder()
                .productId(100L)
                .desc("Tomatoes")
                .price(BigDecimal.valueOf(2))
                .build();
        var product2 = ProductDto.builder()
                .productId(101L)
                .desc("Potatoes")
                .price(BigDecimal.valueOf(3))
                .build();
        products.add(product1);
        products.add(product2);
    }

    public List<ProductDto> find(Long productId) {
        return products.stream()
                //.filter(productDto -> Objects.equals(productId, productDto.getProductId()))
                .collect(Collectors.toList());
    }
}
