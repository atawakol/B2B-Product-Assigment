package com.example.b2b.product.service;

import com.example.b2b.product.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product createProduct(Product prod);
    Product updateProduct(Product prod);
    void deleteProduct(Product product);
    List<Product> getAllProducts();

    Product findById(long prodId);
    List<Product> findByTitle(String prodTitle);
    List<Product> findByDescription(String desc);

    Optional<Product> getById(long prodId);
}
