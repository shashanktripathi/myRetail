package com.casestudy.myRetail.service.impl;

import com.casestudy.myRetail.exception.ProductNotFoundException;
import com.casestudy.myRetail.model.ProductPrice;
import com.casestudy.myRetail.repository.ProductPriceRepository;
import com.casestudy.myRetail.response.ProductPriceResponse;
import com.casestudy.myRetail.service.ProductPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductPriceServiceImpl implements ProductPriceService {

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Override
    public ProductPriceResponse getProductPrice(Long id) {
        ProductPrice productPrice = productPriceRepository.getProductById(id);
        if (productPrice == null) {
            log.error("Product id - {} not found", id);
            throw new ProductNotFoundException("Product id: " + id + " - not found");
        }

        return ProductPriceResponse.builder()
                .value(productPrice.getPrice())
                .currencyCode(productPrice.getCurrencyCodeAsString(productPrice.getCurrencyCode()))
                .build();
    }
}
