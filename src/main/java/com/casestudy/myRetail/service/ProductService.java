package com.casestudy.myRetail.service;

import com.casestudy.myRetail.request.PriceUpdateRequest;
import com.casestudy.myRetail.response.ProductResponse;

public interface ProductService {

    ProductResponse getProduct(Long id);

    ProductResponse updateProductPrice(Long id, PriceUpdateRequest request);
}
