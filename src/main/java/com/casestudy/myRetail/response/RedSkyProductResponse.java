package com.casestudy.myRetail.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RedSkyProductResponse {

    private Long id;

    private String name;
}
