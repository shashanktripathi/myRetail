package com.casestudy.myRetail.service;

import com.casestudy.myRetail.exception.ProductNotFoundException;
import com.casestudy.myRetail.exception.ProductPriceDbException;
import com.casestudy.myRetail.response.ProductResponse;

public interface ProductService {

    ProductResponse getProduct(Long id) throws ProductNotFoundException, ProductPriceDbException;
}
