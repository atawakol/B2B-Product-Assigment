package com.example.b2b.product.rest;

import com.example.b2b.product.model.Product;
import com.example.b2b.product.service.ProductService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    private static final String FIRST_PRODUCT = "First Product";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;


    @Test
    public void givenProduct_whenGetProducts_thenReturnJsonArray()
            throws Exception {

        Product product = new Product();
        product.setTitle(FIRST_PRODUCT);
        product.setVendorUID(1L);

        List<Product> productList = Arrays.asList(product);

        Mockito.when(productService.getAllProducts()).thenReturn(productList);

        mvc.perform(MockMvcRequestBuilders.get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is(FIRST_PRODUCT)));

    }

    @Test
    public void whenPostProduct_thenCreateProduct()
            throws Exception {

        Product product = new Product();
        product.setTitle(FIRST_PRODUCT);
        product.setVendorUID(1L);

        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders.post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(product)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is(FIRST_PRODUCT)));

    }

    private byte[] toJson(Product product) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsBytes(product);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
