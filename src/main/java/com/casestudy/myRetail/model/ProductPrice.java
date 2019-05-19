package com.casestudy.myRetail.model;

import com.casestudy.myRetail.model.enums.CurrencyCodeEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Builder
@Data
@Document(collection = "product_price")
public class ProductPrice {

    public static final String FIELD_PRODUCT_ID = "id";
    public static final String FIELD_PRICE_VALUE = "price";
    public static final String FIELD_CURRENCY_CODE = "currency_code";

    private Long id;

    private BigDecimal price;

    @Field(value = FIELD_CURRENCY_CODE)
    private CurrencyCodeEnum currencyCode;

    public String getCurrencyCodeAsString(CurrencyCodeEnum codeEnum) {
        return codeEnum.getCode();
    }
}
