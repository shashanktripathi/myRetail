package com.casestudy.myRetail.service;

import com.casestudy.myRetail.request.PriceUpdateRequest;
import com.casestudy.myRetail.response.ProductPriceResponse;
import com.casestudy.myRetail.response.ProductResponse;

public interface ProductPriceService {

    ProductPriceResponse getProductPrice(Long id);

    ProductPriceResponse updatePrice(Long id, PriceUpdateRequest request);
}
