package com.example.b2b.product.repository;

import com.example.b2b.product.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RestResource(exported = false)
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByTitle(String title);

    List<Product> findByDescriptionLike(String desc);
}
