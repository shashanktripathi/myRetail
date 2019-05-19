package com.casestudy.myRetail.response;

import com.casestudy.myRetail.response.serialization.MoneySerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ProductPriceResponse {

    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal value;

    @JsonProperty(value = "currency_code")
    private String currencyCode;
}
