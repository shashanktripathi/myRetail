package com.casestudy.myRetail.repository;

import com.casestudy.myRetail.model.ProductPrice;
import com.casestudy.myRetail.model.enums.CurrencyCodeEnum;
import com.casestudy.myRetail.repository.impl.ProductPriceRepositoryImpl;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductPriceRepositoryImplTest {
    @InjectMocks
    private ProductPriceRepositoryImpl productPriceRepository;

    @Mock
    private MongoClient mongoClient;

    @Mock
    private MongoDatabase mockDatabase;

    @Mock
    private MongoCollection<Document> mockCollection;

    @Mock
    private FindIterable<Document> mockFindIterable;

    @Mock
    private MongoCursor<Document> mockCursor;

    private static final String DB_NAME = "db";
    private static final String COLLECTION_NAME = "collection";
    private static final Long PRODUCT_ID = 123L;
    private static final ProductPrice PRODUCT_PRICE = ProductPrice.builder()
            .price(BigDecimal.valueOf(1000d))
            .id(PRODUCT_ID)
            .currencyCode(CurrencyCodeEnum.INR)
            .build();
    private static final Document PRICE_DOCUMENT =
            new Document(ProductPrice.FIELD_PRICE_VALUE, PRODUCT_PRICE.getPrice())
                    .append(ProductPrice.FIELD_CURRENCY_CODE, PRODUCT_PRICE.getCurrencyCode());

    @Before
    public void setup() {
        ReflectionTestUtils.setField(productPriceRepository, "dbName", DB_NAME);
        ReflectionTestUtils.setField(productPriceRepository, "collectionName", COLLECTION_NAME);

        when(mongoClient.getDatabase(anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getCollection(anyString())).thenReturn(mockCollection);
        when(mockCollection.find(any(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.iterator()).thenReturn(mockCursor);
    }

    @Test
    public void shouldReturnProductPriceIfExists() {
        when(mockCursor.hasNext()).thenReturn(true);
        when(mockCursor.next()).thenReturn(PRICE_DOCUMENT);

        ProductPrice productPrice = productPriceRepository.getProductById(PRODUCT_ID);
        assertThat(productPrice, is(PRODUCT_PRICE));
    }

    @Test
    public void shouldReturnNullWhenProductPriceDoesNotExists() {
        when(mockCursor.hasNext()).thenReturn(false);

        ProductPrice price = productPriceRepository.getProductById(PRODUCT_ID);
        assertThat(price, is(nullValue()));
    }
}