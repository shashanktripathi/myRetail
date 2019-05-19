package com.casestudy.myRetail.service.impl;

import com.casestudy.myRetail.response.ProductPriceResponse;
import com.casestudy.myRetail.response.ProductResponse;
import com.casestudy.myRetail.response.RedSkyProductResponse;
import com.casestudy.myRetail.service.ProductPriceService;
import com.casestudy.myRetail.service.ProductService;
import com.casestudy.myRetail.service.RedSkyProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductPriceService productPriceService;

    @Autowired
    private RedSkyProductService redSkyProductService;

    @Override
    public ProductResponse getProduct(Long id) {
        RedSkyProductResponse redSkyProductResponse = redSkyProductService.getProduct(id);
        ProductPriceResponse productPriceResponse = productPriceService.getProductPrice(id);

        return ProductResponse.builder()
                .id(id)
                .name(redSkyProductResponse.getName())
                .currentPrice(productPriceResponse)
                .build();
    }
}
