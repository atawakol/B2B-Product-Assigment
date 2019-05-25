package com.example.b2b.product.service;

import com.example.b2b.product.exceptions.ProductNotFoundException;
import com.example.b2b.product.model.Product;
import com.example.b2b.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Transactional Service class that handle all product functionality.
 *
 * @author atawakol
 */
@Service
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT_ID_NOT_FOUND = "Product with id {} Not found";
    private static final String WILD_CARD = "%";

    @Autowired
    private ProductRepository productRepository;


    @Override
    public void createProduct(Product prod) {
        log.info("Insert a new Product");
        productRepository.save(prod);
    }

    @Override
    public void updateProduct(Product prod) {
        // Custom logic for update can be added here.
        log.info("update Product with id {} ", prod.getProductId());
        productRepository.save(prod);
    }

    @Override
    public void deleteProduct(long prodId) {
        log.info("delete Product with id {} ", prodId);
        productRepository.deleteById(prodId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        log.trace("get All Products");
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(p -> products.add(p));
        return products;
    }

    @Override
    public Product findById(long prodId) {
        return productRepository.findById(prodId)
                .orElseThrow(() -> new ProductNotFoundException(prodId));
        //return productRepository.findById(prodId);
    }

    @Override
    public Optional<Product> getById(long prodId) {
        return productRepository.findById(prodId);
    }

    @Override
    public List<Product> findByTitle(String prodTitle) {
        return productRepository.findByTitle(prodTitle);
    }

    @Override
    public List<Product> findByDescription(String desc) {
        return productRepository.findByDescriptionLike(WILD_CARD + desc + WILD_CARD);
    }
}