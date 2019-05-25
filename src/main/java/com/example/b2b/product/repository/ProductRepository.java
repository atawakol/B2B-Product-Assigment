package com.example.b2b.product.repository;

import com.example.b2b.product.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByTitle(String title);

    List<Product> findByDescriptionLike(String desc);
}
