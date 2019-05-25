package com.example.b2b.product.exceptions;

/**
 * @author atawakol
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(long id) {
        super("Could not find product " + id);
    }
}
