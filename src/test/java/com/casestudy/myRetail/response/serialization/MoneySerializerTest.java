package com.casestudy.myRetail.response.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class MoneySerializerTest {

    private MoneySerializer moneySerializer = new MoneySerializer();

    @Mock
    private JsonGenerator mockJsonGenerator;

    @Captor
    private ArgumentCaptor<String> priceCaptor;

    @Before
    public void setup() throws Exception {
        doNothing().when(mockJsonGenerator).writeString(priceCaptor.capture());
    }

    @Test
    public void shouldRoundUpToTwoDecimals() throws Exception {
        moneySerializer.serialize(new BigDecimal(10.14555), mockJsonGenerator, null);
        assertThat(priceCaptor.getValue(), is("10.15"));
    }


    @Test
    public void shouldRoundDownToTwoDecimals() throws  Exception {
        moneySerializer.serialize(new BigDecimal(0.33333), mockJsonGenerator, null);
        assertThat(priceCaptor.getValue(), is("0.33"));
    }


    @Test
    public void shouldAddDecimalToWholeNumber() throws Exception {
        moneySerializer.serialize(new BigDecimal(1000), mockJsonGenerator, null);
        assertThat(priceCaptor.getValue(), is("1000.00"));
    }
}