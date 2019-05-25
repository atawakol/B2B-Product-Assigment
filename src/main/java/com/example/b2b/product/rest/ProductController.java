package com.example.b2b.product.rest;

import com.example.b2b.product.model.Product;
import com.example.b2b.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private static final String PRODUCT_PATH = "/products/{id}/";
    @Autowired
    private ProductService productService;

    @GetMapping()
    public List<Product> listAllProducts() {
        log.trace("List All Products started .... ");
        List<Product> products = productService.getAllProducts();
        log.debug("Products size" + products.size());

        return products;
    }

    @GetMapping("/{productId}/")
    public Product getProduct(@PathVariable("productId") long id) {
        log.debug("get product {}", id);
        return productService.findById(id);
    }

    @GetMapping(value="/filters/")
    public ResponseEntity<List<Product>> filterProducts(@RequestParam(name = "title", defaultValue="") String title,
                                                        @RequestParam(name = "description", defaultValue="") String desc) {

        log.debug("filter products ");

        List<Product> products = null;
        if(!"".equals(title.trim())) {
            products = productService.findByTitle(title);
        }
        else if (!"".equals(desc.trim())){
            products = productService.findByDescription(desc);
        }

        if (products == null || products.size() == 0) {
            return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Void> createProduct(@Valid @RequestBody Product product, UriComponentsBuilder uric) {

        log.debug("create product .... ");
        productService.createProduct(product);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uric.path(PRODUCT_PATH).buildAndExpand(product.getProductId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value="/{productId}/")
    public ResponseEntity<Product> updateProduct(@PathVariable("productId") long id, @Valid @RequestBody Product newProduct) {

        log.debug("update product product .... ");

        return productService.getById(id)
                .map(product -> {
                    product.setDescription(newProduct.getDescription());
                    product.setImagePath(newProduct.getImagePath());
                    product.setNumberOfViews(newProduct.getNumberOfViews());
                    product.setPrice(newProduct.getPrice());
                    product.setTitle(newProduct.getTitle());
                    product.setVendorUID(newProduct.getVendorUID());
                    product.setDietaryFlags(newProduct.getDietaryFlags());

                    productService.updateProduct(product);
                    return new ResponseEntity<Product>(product, HttpStatus.OK);
                })
                .orElseGet(
                        () ->  new ResponseEntity<Product>(HttpStatus.NOT_FOUND)
                );
    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
