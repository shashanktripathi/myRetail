package com.casestudy.myRetail.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductResponse {

    private Long id;

    private String name;

    @JsonProperty(value = "current_price")
    private ProductPriceResponse currentPrice;
}
