package org.example.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

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
