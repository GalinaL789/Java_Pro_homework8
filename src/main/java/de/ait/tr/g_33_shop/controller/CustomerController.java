package de.ait.tr.g_33_shop.controller;

import de.ait.tr.g_33_shop.domain.entity.Customer;
import de.ait.tr.g_33_shop.service.interfaces.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }


    @PostMapping
    public Customer save(@RequestBody Customer customer) {
        return service.save(customer);
    }

//    @GetMapping
//    public List<Customer> get(@RequestParam(required = false) Long id) {
//        if (id == null) {
//            return service.getAllActiveCustomers();
//        } else {
//            Customer customer = service.getById(id);
//           List<Customer> customers = new ArrayList<>();
//           customers.add(customer);
//           return customers;
//        }
//    }
@GetMapping
public List<Customer> get(@RequestParam(required = false) Long id) {
    if (id == null) {
        return service.getAllActiveCustomers();
    } else {
        Customer customer = service.getById(id);
        if (customer != null) {
            List<Customer> customers = new ArrayList<>();
            customers.add(customer);
            return customers;
        } else {
            return new ArrayList<>(); // Возвращаем пустой список, если сущность не найдена
        }
    }
}

    @PutMapping
    public Customer update(@RequestBody Customer customer) {

        return service.update(customer);
    }

    @DeleteMapping
    public void delete(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name
    ) {
        if (id != null) {
            service.deleteById(id);
        } else if (name != null) {
            // service.deleteByName(name);
        }
    }

    @PutMapping("/restore")
    public void restore(@RequestParam Long id) {
        service.restoreById(id);
    }

    @GetMapping("/all")
    public long getActiveCustomersNumber() {
        // return service.getAllActiveCustomers().size();
        return service.getActiveCustomersNumber();
    }

    @GetMapping("/averagecost")
    public BigDecimal getAverageCostOfCustomersProducts(@RequestParam Long customerId) {
        return null;
        //service.getAverageCostOfCustomersProducts(customerId);
    }

    @PutMapping("/addproduct")
    public void addProductToCustomersCart(@RequestParam Long customerId, @RequestParam
    Long productId) {
        //service.addProductToCustomersCart(customerId, productId);
    }

    @DeleteMapping("/deletedproduct")
    public void removeProductFromCustomersCart(@RequestParam Long customerId, @RequestParam Long productId) {
        //service.removeProductFromCustomersCart(customerId, productId);
    }

    @DeleteMapping("/deletecart")
    public void clearCustomersCart(@RequestParam Long customerId) {
        //service.clearCustomersCart(customerId);
    }

}

