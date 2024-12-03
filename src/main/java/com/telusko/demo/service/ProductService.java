package com.telusko.demo.service;

import com.telusko.demo.model.Product;
import com.telusko.demo.repo.ProductRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(int id) {
        return productRepo.findById(id).get();
    }

    @SneakyThrows
    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getName());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return productRepo.save(product);

    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException{
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageDate(imageFile.getBytes());
        product.setImageType(imageFile.getContentType());
        return productRepo.save(product);
    }

    public void deleteProduct(int id) {
        productRepo.deleteById(id);

    }

    public List<Product> searchProducts(String keyword) {
         return productRepo.searchProducts(keyword);
    }
}
