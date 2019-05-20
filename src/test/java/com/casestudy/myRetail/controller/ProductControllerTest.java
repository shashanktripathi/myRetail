package com.casestudy.myRetail.controller;

import com.casestudy.myRetail.exception.ProductNotFoundException;
import com.casestudy.myRetail.exception.ProductPriceDbException;
import com.casestudy.myRetail.model.enums.CurrencyCodeEnum;
import com.casestudy.myRetail.response.ProductPriceResponse;
import com.casestudy.myRetail.response.ProductResponse;
import com.casestudy.myRetail.response.RedSkyProductResponse;
import com.casestudy.myRetail.service.ProductPriceService;
import com.casestudy.myRetail.service.RedSkyProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductPriceService productPriceService;

    @MockBean
    private RedSkyProductService redSkyProductService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Long PRODUCT_ID = 12345L;
    private static final String PRODUCT_NAME = "This is a test product name!";
    private static final BigDecimal PRICE_VALUE = new BigDecimal(17.5555555);
    private static final CurrencyCodeEnum CURRENCY = CurrencyCodeEnum.USD;
    private static final ProductPriceResponse PRODUCT_PRICE_RESPONSE = ProductPriceResponse.builder()
            .value(PRICE_VALUE)
            .currencyCode(CURRENCY.getCode())
            .build();
    private static final RedSkyProductResponse RED_SKY_PRODUCT_RESPONSE = RedSkyProductResponse.builder()
            .id(PRODUCT_ID)
            .name(PRODUCT_NAME)
            .build();
    private static final ProductResponse PRODUCT_RESPONSE = ProductResponse.builder()
            .id(PRODUCT_ID)
            .name(PRODUCT_NAME)
            .currentPrice(PRODUCT_PRICE_RESPONSE)
            .build();

    @Test
    public void shouldReturnSuccessReponseWhenValidData() throws Exception {
        when(redSkyProductService.getProduct(PRODUCT_ID)).thenReturn(RED_SKY_PRODUCT_RESPONSE);
        when(productPriceService.getProductPrice(PRODUCT_ID)).thenReturn(PRODUCT_PRICE_RESPONSE);

        MvcResult result = this.mockMvc.perform(get("/products/" + PRODUCT_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String actualResponse = result.getResponse().getContentAsString();
        Assert.assertEquals(objectMapper.writeValueAsString(PRODUCT_RESPONSE), actualResponse);
    }

    @Test
    public void shouldReturn404ReponseWhenRedSkyProductDoesNotHaveProductData() throws Exception {
        when(redSkyProductService.getProduct(PRODUCT_ID)).thenThrow(new ProductNotFoundException(""));

        this.mockMvc.perform(get("/products/" + PRODUCT_ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void shouldReturn404ReponseWhenProductPriceDbDoesNotHaveProductData() throws Exception {
        when(redSkyProductService.getProduct(PRODUCT_ID)).thenReturn(RED_SKY_PRODUCT_RESPONSE);
        when(productPriceService.getProductPrice(PRODUCT_ID)).thenThrow(new ProductNotFoundException(""));

        this.mockMvc.perform(get("/products/" + PRODUCT_ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void shouldReturn500ReponseWhenProductPriceDbHasErrorOrTimeout() throws Exception {
        when(redSkyProductService.getProduct(PRODUCT_ID)).thenReturn(RED_SKY_PRODUCT_RESPONSE);
        when(productPriceService.getProductPrice(PRODUCT_ID)).thenThrow(new ProductPriceDbException(""));

        this.mockMvc.perform(get("/products/" + PRODUCT_ID))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andReturn();
    }
}
