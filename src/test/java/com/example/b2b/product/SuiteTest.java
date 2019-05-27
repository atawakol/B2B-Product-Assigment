package com.example.b2b.product;

import com.example.b2b.product.integrationTests.ProductApplicationTests;
import com.example.b2b.product.repository.ProductRepositoryTest;
import com.example.b2b.product.rest.ProductControllerTest;
import com.example.b2b.product.service.ProductServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ProductRepositoryTest.class, ProductControllerTest.class, ProductServiceImplTest.class,
        ProductApplicationTests.class})
public class SuiteTest {

}
