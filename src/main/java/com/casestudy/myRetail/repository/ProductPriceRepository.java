package com.casestudy.myRetail.repository;

import com.casestudy.myRetail.model.ProductPrice;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPriceRepository {

    ProductPrice getProductById(Long id);

}
