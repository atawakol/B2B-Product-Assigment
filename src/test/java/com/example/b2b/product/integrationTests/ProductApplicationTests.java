package com.example.b2b.product.integrationTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.b2b.product.ProductApplication;
import com.example.b2b.product.model.Product;
import com.example.b2b.product.repository.ProductRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.stream.StreamSupport;

/**
 * This is sample integration test class that tests the full layers of spring.
 *
 * Ideally, this class should be in its own project so that Integration tests can run on their own schedule.
 * Here I create it for illustration purposes.
 *
 * This class is using application.properties from test folder which means it executes against H2 DB. However, in its own
 * project it can has another configuration file and can be executed against MySQL.
 *
 *
 * @author atawakol
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = ProductApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ProductApplicationTests {

	private static final String API_URL = "/api/products";
	private static final String PRODUCT_1 = "Product 1";
	private static final String PRODUCT_2 = "Product 2";
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ProductRepository repository;

	@Before
	public void createDbRecords() {
		createTestProduct(PRODUCT_1);
		createTestProduct(PRODUCT_2);
	}

	@After
	public void resetDb() {
		repository.deleteAll();
	}

	@Test
	public void whenValidInput_thenCreateProduct() throws IOException, Exception {
		Product product = new Product();
		product.setTitle("Product 3");
		product.setVendorUID(1L);

		mvc.perform(post(API_URL)
				.contentType(MediaType.APPLICATION_JSON).content(toJson(product)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.title", Matchers.is("Product 3")));

		Iterable<Product> found = repository.findAll();
		Assert.assertEquals(3,
				StreamSupport.stream(found.spliterator(), false).count());

	}

	@Test
	public void givenProducts_whenGetProducts_thenStatus200() throws Exception {

		mvc.perform(get(API_URL).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", Matchers.hasSize(Matchers.greaterThanOrEqualTo(2))))
				.andExpect(jsonPath("$[0].title", Matchers.is(PRODUCT_1)))
				.andExpect(jsonPath("$[1].title", Matchers.is(PRODUCT_2)));

	}

	private void createTestProduct(String name) {
		Product prod = new Product();
		prod.setVendorUID(1L);
		prod.setTitle(name);
		repository.save(prod);
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
