package com.example.b2b.product.exceptions;

/**
 * Exception that is thrown in case of product not found
 *
 * @author atawakol
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(long id) {
        super("Could not find product " + id);
    }
}
