package com.casestudy.myRetail.service;

import com.casestudy.myRetail.exception.InvalidUpdateException;
import com.casestudy.myRetail.exception.ProductNotFoundException;
import com.casestudy.myRetail.exception.ProductPriceDbException;
import com.casestudy.myRetail.model.ProductPrice;
import com.casestudy.myRetail.model.enums.CurrencyCodeEnum;
import com.casestudy.myRetail.repository.ProductPriceRepository;
import com.casestudy.myRetail.request.PriceUpdateRequest;
import com.casestudy.myRetail.request.ProductPriceRequest;
import com.casestudy.myRetail.response.ProductPriceResponse;
import com.casestudy.myRetail.service.impl.ProductPriceServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
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
    private static final ProductPriceRequest PRODUCT_PRICE_REQUEST = ProductPriceRequest.builder()
            .value(BigDecimal.valueOf(PRICE))
            .currencyCode(PRODUCT_PRICE_CURRENCY.getCode())
            .build();
    @Test
    public void testGetProductPriceSuccess() {
        when(productPriceRepository.getProductById(PRODUCT_ID)).thenReturn(PRODUCT_PRICE);
        ProductPriceResponse response = productPriceService.getProductPrice(PRODUCT_ID);
        assertThat(response, is(PRODUCT_PRICE_RESPONSE));
    }

    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowExceptionWhenProductPriceRepoReturnsNull() {
        when(productPriceRepository.getProductById(PRODUCT_ID)).thenReturn(null);
        productPriceService.getProductPrice(PRODUCT_ID);
    }

    @Test
    public void testUpdateProductPriceSuccess() {
        when(productPriceRepository.updatePriceByProductId(PRODUCT_ID, PRODUCT_PRICE_REQUEST)).thenReturn(PRODUCT_PRICE);
        ProductPriceResponse response = productPriceService.updatePrice(PRODUCT_ID, PriceUpdateRequest.builder()
                .id(PRODUCT_ID)
                .currentPrice(PRODUCT_PRICE_REQUEST)
                .build());
        assertThat(response, is(PRODUCT_PRICE_RESPONSE));
    }

    @Test(expected = InvalidUpdateException.class)
    public void shouldThrowInvalidUpdateExceptionWhenIdMismatch() {
        productPriceService.updatePrice(1L, PriceUpdateRequest.builder()
                .id(PRODUCT_ID)
                .currentPrice(PRODUCT_PRICE_REQUEST)
                .build());
    }

    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowExceptionWhenProductIdDoesNotExists() {
        when(productPriceRepository.updatePriceByProductId(PRODUCT_ID, PRODUCT_PRICE_REQUEST)).thenReturn(null);
        productPriceService.updatePrice(PRODUCT_ID, PriceUpdateRequest.builder()
                .id(PRODUCT_ID)
                .currentPrice(PRODUCT_PRICE_REQUEST)
                .build());
    }

    @Test(expected = ProductPriceDbException.class)
    public void shouldThrowExceptionWhenProductPriceDbIsDown() {
        when(productPriceRepository.updatePriceByProductId(PRODUCT_ID, PRODUCT_PRICE_REQUEST))
                .thenThrow(new ProductPriceDbException(""));
        productPriceService.updatePrice(PRODUCT_ID, PriceUpdateRequest.builder()
                .id(PRODUCT_ID)
                .currentPrice(PRODUCT_PRICE_REQUEST)
                .build());
    }

}