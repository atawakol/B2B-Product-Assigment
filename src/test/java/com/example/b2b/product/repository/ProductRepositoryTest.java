package com.example.b2b.product.repository;

import static org.junit.Assert.*;

import com.example.b2b.product.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setup(){

    }

    @Test
    public void whenFindByTitle_thenReturnProduct() {
        // given
        Product product = new Product();
        product.setVendorUID(1L);
        product.setTitle("First Product");
        product.setDescription("Product description");
        entityManager.persist(product);
        entityManager.flush();

        // when
        List<Product> result = productRepository.findByTitle(product.getTitle());

        // then
        assertEquals(1, result.size());
        assertEquals(product.getTitle(), result.get(0).getTitle());

    }

    @Test
    public void whenFindByDescription_thenReturnAlikeProduct() {
        // given
        Product product = new Product();
        product.setVendorUID(1L);
        product.setTitle("Second Product");
        product.setDescription("Product description like");
        entityManager.persist(product);
        entityManager.flush();

        // when
        List<Product> result = productRepository.findByDescriptionLike("%description%");

        // then
        assertEquals(1, result.size());
        assertEquals(product.getTitle(), result.get(0).getTitle());

    }
}
