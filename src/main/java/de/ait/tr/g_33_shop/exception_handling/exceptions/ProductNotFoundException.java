package de.ait.tr.g_33_shop.exception_handling.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long productId) {
        super(String.format("Product with id: %id not found"+productId));
    }
    public ProductNotFoundException(String productTitle) {
        super(String.format("Product with title: %s not found"+productTitle));
    }
}
