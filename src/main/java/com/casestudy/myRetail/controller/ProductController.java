package com.casestudy.myRetail.controller;

import com.casestudy.myRetail.request.PriceUpdateRequest;
import com.casestudy.myRetail.response.ProductResponse;
import com.casestudy.myRetail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ProductResponse getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @PutMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ProductResponse updateProductPrice(@PathVariable Long productId,
                                                            @RequestBody PriceUpdateRequest request) {
        return productService.updateProductPrice(productId, request);
    }
}
