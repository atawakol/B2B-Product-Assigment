package com.example.b2b.product.service;

import static org.junit.Assert.*;

import com.example.b2b.product.exceptions.ProductNotFoundException;
import com.example.b2b.product.model.Product;
import com.example.b2b.product.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class ProductServiceImplTest {


    private static final String FIRST_PRODUCT = "First Product";
    private static final String SECOND_PRODUCT = "Second Product";
    private static final String Thrid_PRODUCT = "Third Product";
    private static final String SAVE_ERROR_MESSAGE = "The title can't be empty";
    private static final Long ID = 1L;
    private static final Long NOT_EXISIT_ID = 5L;

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Before
    public void setUp(){
        Product product = createProduct(FIRST_PRODUCT, null);
        Mockito.when(productRepository.findByTitle(product.getTitle()))
                .thenReturn(Arrays.asList(product));

        Product savedProduct = createProduct(SECOND_PRODUCT, null);
        Mockito.when(productRepository.save(savedProduct))
                .thenReturn(savedProduct);

        Product savedWrongProduct = createProduct(null, null);
        Mockito.when(productRepository.save(savedWrongProduct))
                .thenThrow(new IllegalArgumentException(SAVE_ERROR_MESSAGE));

        Product productHasId = createProduct(Thrid_PRODUCT, new BigDecimal(10));
        setProductField(productHasId);
        Mockito.when(productRepository.findById(productHasId.getProductId()))
                .thenReturn(Optional.of(productHasId));
    }

    @Test
    public void whenValidProduct_thenSaveProductShouldWork() {
        Product result = productService
                .createProduct(createProduct(SECOND_PRODUCT, null));

        assertEquals(result.getTitle(), SECOND_PRODUCT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNotValidProduct_thenSaveProductShouldThrow() {
        Product result = productService
                .createProduct(createProduct(null, null));
    }

    @Test
    public void whenValidTitle_thenProductShouldBeFound() {
        List<Product> result = productService.findByTitle(FIRST_PRODUCT);

        assertEquals(1, result.size());
        assertEquals(FIRST_PRODUCT, result.get(0).getTitle());
    }

    @Test(expected = ProductNotFoundException.class)
    public void whenNotValidTitle_thenProductNotFoundExceptionShouldThrow() {
        Product product = productService.findById(NOT_EXISIT_ID);
    }

    @Test
    public void whenValidID_thenProductShouldBeFound() {
        Product product = productService.findById(ID);
        assertEquals(Thrid_PRODUCT, product.getTitle());
    }



    @TestConfiguration
    static class ProductServiceImplTestConfiguration {
        @Bean
        public ProductService productService(){
            return new ProductServiceImpl();
        }
    }

    private Product createProduct(String title, BigDecimal price) {
        Product product = new Product();
        product.setVendorUID(1L);
        product.setTitle(title);
        product.setPrice(price);
        return product;
    }

    private void setProductField(Product productHasId) {
        try {
            FieldSetter.setField(productHasId,
                    productHasId.getClass().getDeclaredField("productId"), ID);
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
