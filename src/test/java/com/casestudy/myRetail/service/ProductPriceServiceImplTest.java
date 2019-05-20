package com.casestudy.myRetail.service;

import com.casestudy.myRetail.exception.ProductNotFoundException;
import com.casestudy.myRetail.model.ProductPrice;
import com.casestudy.myRetail.model.enums.CurrencyCodeEnum;
import com.casestudy.myRetail.repository.ProductPriceRepository;
import com.casestudy.myRetail.response.ProductPriceResponse;
import com.casestudy.myRetail.service.impl.ProductPriceServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductPriceServiceImplTest {

    @InjectMocks
    private ProductPriceServiceImpl productPriceService;

    @Mock
    private ProductPriceRepository productPriceRepository;

    private static final Long PRODUCT_ID = 123566L;
    private static final Double PRICE = 155.555;
    private static final CurrencyCodeEnum PRODUCT_PRICE_CURRENCY = CurrencyCodeEnum.INR;
    private static final ProductPrice PRODUCT_PRICE = ProductPrice.builder()
            .id(PRODUCT_ID)
            .price(BigDecimal.valueOf(PRICE))
            .currencyCode(PRODUCT_PRICE_CURRENCY)
            .build();

    private static final ProductPriceResponse PRODUCT_PRICE_RESPONSE = ProductPriceResponse.builder()
            .value(BigDecimal.valueOf(PRICE))
            .currencyCode(PRODUCT_PRICE_CURRENCY.getCode())
            .build();
    @Test
    public void testGetProductPriceSuccess() {
        when(productPriceRepository.getProductById(PRODUCT_ID)).thenReturn(PRODUCT_PRICE);
        ProductPriceResponse response = productPriceService.getProductPrice(PRODUCT_ID);
        assertThat(response, Matchers.is(PRODUCT_PRICE_RESPONSE));
    }

    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowExceptionWhenProductPriceRepoReturnsNull() {
        when(productPriceRepository.getProductById(PRODUCT_ID)).thenReturn(null);
        productPriceService.getProductPrice(PRODUCT_ID);
    }

}