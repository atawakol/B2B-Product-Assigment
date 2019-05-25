package com.example.b2b.product.service;

import com.example.b2b.product.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void createProduct(Product prod);
    void updateProduct(Product prod);
    void deleteProduct(long prodId);
    List<Product> getAllProducts();

    Product findById(long prodId);
    List<Product> findByTitle(String prodTitle);
    List<Product> findByDescription(String desc);

    Optional<Product> getById(long prodId);
}
