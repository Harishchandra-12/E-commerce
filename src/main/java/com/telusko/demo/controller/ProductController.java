package com.telusko.demo.controller;


import com.telusko.demo.model.Product;
import com.telusko.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String greet() {
        return "Hello....";
    }

    @RequestMapping("/products")
    public List<Product> getAllProducts() {

        return productService.getAllProducts();

    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product pro = productService.getProductById(id);

        if(pro!=null)
            return new ResponseEntity<>(pro, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        try {
            Product product1 = productService.addProduct(product,imageFile);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id) {
        Product product = productService.getProductById(id);
        byte[] b = product.getImageDate();
        return  ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType()))
                .body(b);
    }

    @PostMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,
                                                @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile) {
        Product product1 = null;
        try {
            product1 = productService.updateProduct(id,product,imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to Update",HttpStatus.NOT_FOUND);
        }

        if(product1 !=null ) {
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Failed to Update",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Product product = productService.getProductById(id);

        if(product!=null) {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted Successfully....",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Object Not found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/search/{keyword}")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword) {
        List<Product> list = productService.searchProducts(keyword);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
}
