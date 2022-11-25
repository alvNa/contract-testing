package org.example.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Data
public class PriceDto implements Serializable {
    Long productId;
    BigDecimal price;
}
