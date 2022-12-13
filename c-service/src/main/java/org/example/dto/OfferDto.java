package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferDto implements Serializable {
    Long offerId;
    Long productId;
    LocalDate from;
    LocalDate to;
    String promotionDesc;
    Integer percentage;
}
