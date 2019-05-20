package com.casestudy.myRetail.repository;

import com.casestudy.myRetail.model.ProductPrice;
import com.casestudy.myRetail.request.ProductPriceRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPriceRepository {

    ProductPrice getProductById(Long id);

    ProductPrice updatePriceByProductId(Long id, ProductPriceRequest productPriceRequest);
}
