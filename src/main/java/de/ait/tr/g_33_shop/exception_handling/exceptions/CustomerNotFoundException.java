package de.ait.tr.g_33_shop.exception_handling.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long customerId) {
        super(String.format("Customer with id %d was not found", customerId));
    }
    public CustomerNotFoundException(String customerName) {
        super(String.format("Customer with the name: %s was not found", customerName));
    }

}
