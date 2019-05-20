package com.casestudy.myRetail.service;

import com.casestudy.myRetail.exception.ProductNotFoundException;
import com.casestudy.myRetail.model.enums.CurrencyCodeEnum;
import com.casestudy.myRetail.response.ProductPriceResponse;
import com.casestudy.myRetail.response.ProductResponse;
import com.casestudy.myRetail.response.RedSkyProductResponse;
import com.casestudy.myRetail.service.impl.ProductServiceImpl;
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
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductPriceService productPriceService;

    @Mock
    private RedSkyProductService redSkyProductService;

    private static final Long PRODUCT_ID = 123566L;
    private static final String PRODUCT_NAME = "A Random Product Name";
    private static final BigDecimal PRODUCT_PRICE = new BigDecimal(155.555);
    private static final CurrencyCodeEnum PRODUCT_PRICE_CURRENCY = CurrencyCodeEnum.INR;
    private static final ProductPriceResponse PRODUCT_PRICE_RESPONSE = ProductPriceResponse.builder()
            .value(PRODUCT_PRICE)
            .currencyCode(PRODUCT_PRICE_CURRENCY.getCode())
            .build();
    private static final ProductResponse TEST_PRODUCT_PRICE = ProductResponse.builder()
            .id(PRODUCT_ID)
            .name(PRODUCT_NAME)
            .currentPrice(PRODUCT_PRICE_RESPONSE)
            .build();

    @Test
    public void testGetProductSuccess() {
        when(productPriceService.getProductPrice(PRODUCT_ID)).thenReturn(PRODUCT_PRICE_RESPONSE);
        when(redSkyProductService.getProduct(PRODUCT_ID)).thenReturn(RedSkyProductResponse.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .build());

        ProductResponse product = productService.getProduct(PRODUCT_ID);
        assertThat(product, is(TEST_PRODUCT_PRICE));
    }

    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowExceptionWhenProductPriceServiceThrowsException() {
        when(redSkyProductService.getProduct(PRODUCT_ID)).thenReturn(RedSkyProductResponse.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .build());
        when(productPriceService.getProductPrice(PRODUCT_ID)).thenThrow(new ProductNotFoundException(""));
        productService.getProduct(PRODUCT_ID);
    }

    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowExceptionWhenRedSkyProductServiceThrowsException() {
        when(redSkyProductService.getProduct(PRODUCT_ID)).thenThrow(new ProductNotFoundException(""));
        productService.getProduct(PRODUCT_ID);
    }
}