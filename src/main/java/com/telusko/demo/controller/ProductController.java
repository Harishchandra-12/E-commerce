package com.telusko.demo.controller;


import com.telusko.demo.model.Product;
import com.telusko.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping("/")
    public String greet() {
        return "Hello....";
    }

    @RequestMapping("/products")
    public List<Product> getAllProducts() {

        return productService.getAllProducts();

    }
}
