package com.casestudy.myRetail.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PriceUpdateRequest {

    private Long id;

    private String name;

    @JsonProperty(value = "current_price")
    private ProductPriceRequest currentPrice;
}
