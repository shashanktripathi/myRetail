package com.casestudy.myRetail.service.impl;

import com.casestudy.myRetail.exception.ProductNotFoundException;
import com.casestudy.myRetail.response.RedSkyProductResponse;
import com.casestudy.myRetail.service.RedSkyProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RedSkyProductServiceImpl implements RedSkyProductService {

    @Value("${redsky.target.product.url}")
    private
    String redSkyProductUrl;

    @Value("${redsky.target.product.url.params}")
    private
    String redSkyProductUrlParams;

    @Override
    public RedSkyProductResponse getProduct(Long id) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", id.toString());

        ObjectMapper mapper = new ObjectMapper();

        String productUrl = redSkyProductUrl + id;

        try {
            log.info("Calling RedSky product API for id: {}. Url: {}", id, productUrl);
            ResponseEntity<String> response
                    = restTemplate.getForEntity(productUrl, String.class, id);

            Map<String, Map> data = mapper.readValue(response.getBody(), Map.class);

            Map<String, Map> productMap = data.get("product");
            Map<String, Map> itemMap = productMap.get("item");
            Map<String, String> descMap = itemMap.get("product_description");
            String title = descMap.get("title");

            return RedSkyProductResponse.builder()
                    .id(id)
                    .name(title)
                    .build();
        } catch (Exception e) {
            log.error("Product id - {} not found", id);
            throw new ProductNotFoundException("Product id: " + id + " - not found");
        }
    }
}
