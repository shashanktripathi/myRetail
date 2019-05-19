package com.casestudy.myRetail.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping(value = "/")
    public String home() {
        return "Hello!!";
    }

}
