package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OfferDto implements Serializable {
    Long offerId;
    Long productId;
    LocalDate from;
    LocalDate to;
    String promotionDesc;
    Integer percentage;
}
