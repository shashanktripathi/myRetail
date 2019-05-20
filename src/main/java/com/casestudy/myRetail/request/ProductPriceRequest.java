package com.casestudy.myRetail.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ProductPriceRequest {

    private BigDecimal value;

    @JsonProperty(value = "currency_code")
    private String currencyCode;
}
