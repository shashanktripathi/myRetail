package com.casestudy.myRetail.repository.impl;

import com.casestudy.myRetail.exception.ProductPriceDbException;
import com.casestudy.myRetail.model.ProductPrice;
import com.casestudy.myRetail.model.enums.CurrencyCodeEnum;
import com.casestudy.myRetail.repository.ProductPriceRepository;
import com.casestudy.myRetail.request.ProductPriceRequest;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

import static com.casestudy.myRetail.model.ProductPrice.*;
import static com.mongodb.client.model.Filters.eq;

@Slf4j
@Repository
public class ProductPriceRepositoryImpl implements ProductPriceRepository {

    private MongoClient mongoClient;

    @Value("${mongo.db}")
    private String dbName;

    @Value("${mongo.collection}")
    private String collectionName;

    public ProductPriceRepositoryImpl(@NonNull MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public ProductPrice getProductById(Long productId) {
        try {
            ProductPrice productPrice = null;

            MongoCollection<Document> collection = getCollection();
            MongoCursor<Document> documents = collection.find(eq(FIELD_PRODUCT_ID, productId)).iterator();

            if (documents.hasNext()) {
                Document priceDoc = documents.next();
                productPrice = builder()
                        .id(productId)
                        .price(new BigDecimal(String.valueOf(priceDoc.get(FIELD_PRICE_VALUE))))
                        .currencyCode(CurrencyCodeEnum.valueOf(String.valueOf(priceDoc.get(FIELD_CURRENCY_CODE))))
                        .build();
            }

            return productPrice;
        } catch (Exception e) {
            log.error("Unexpected mongo error while fetch product price for product id: {}. Err: {}", productId, e);
            throw new ProductPriceDbException(e.getMessage());
        }

    }

    @Override
    public ProductPrice updatePriceByProductId(Long id, ProductPriceRequest request) {
        try {
            MongoCollection<Document> collection = getCollection();
            UpdateResult result = collection.updateOne(eq(FIELD_PRODUCT_ID, id),
                    new Document("$set", new Document(FIELD_PRICE_VALUE, request.getValue())));
            if (result.getMatchedCount() > 0) {
                return ProductPrice.builder()
                        .id(id)
                        .price(request.getValue())
                        .currencyCode(CurrencyCodeEnum.valueOf(request.getCurrencyCode()))
                        .build();
            }
        } catch (Exception e) {
            log.error("Unexpected mongo error while fetch product price for product id: {}. Err: {}", id, e);
            throw new ProductPriceDbException(e.getMessage());
        }
        return null;
    }

    private MongoCollection<Document> getCollection() {
        MongoDatabase db = mongoClient.getDatabase(dbName);
        return db.getCollection(collectionName);
    }
}
