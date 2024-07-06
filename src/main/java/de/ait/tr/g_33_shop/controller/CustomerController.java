package de.ait.tr.g_33_shop.controller;

import de.ait.tr.g_33_shop.domain.dto.CustomerDto;
import de.ait.tr.g_33_shop.service.interfaces.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer controller", description = "Controller for various operations with Customers")

public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }


    @PostMapping
    public CustomerDto save(@RequestBody CustomerDto customer) {
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
public List<CustomerDto> get(@RequestParam(required = false) Long id) {
    if (id == null) {
        return service.getAllActiveCustomers();
    } else {
        CustomerDto customer = service.getById(id);
        if (customer != null) {
            List<CustomerDto> customers = new ArrayList<>();
            customers.add(customer);
            return customers;
        } else {
            return new ArrayList<>(); // Возвращаем пустой список, если сущность не найдена
        }
    }
}

    @PutMapping
    public CustomerDto
    update(@RequestBody CustomerDto customer) {
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


    @GetMapping("/average-cost")
    public BigDecimal getAverageCostOfCustomersProducts(@RequestParam Long customerId) {
        return null;
        //service.getAverageCostOfCustomersProducts(customerId);
    }

    @PutMapping("/add-product")
    public void addProductToCustomersCart(@RequestParam Long customerId, @RequestParam
    Long productId) {
        //service.addProductToCustomersCart(customerId, productId);
    }

    @DeleteMapping("/remove-product")
    public void removeProductFromCustomersCart(@RequestParam Long customerId, @RequestParam Long productId) {
        //service.removeProductFromCustomersCart(customerId, productId);
    }

    @DeleteMapping("/clear-cart")
    public void clearCustomersCart(@RequestParam Long customerId) {
        //service.clearCustomersCart(customerId);
    }
    @GetMapping("/number")
    public long getActiveCustomersNumber(){
        return service.getActiveCustomersNumber();
    }

    @GetMapping("{customerId}/products_cost")
      public BigDecimal getTotalCostOfCustomersProducts(@PathVariable long customerId) {
        return service.getTotalCostOfCustomersProducts(customerId);
      }
    @GetMapping("{customerId}/aver_product_cost")
    public BigDecimal getAverageCostOfCustomersProducts(@PathVariable long customerId) {
        return service.getAverageCostOfCustomersProducts(customerId);
    }
@PutMapping("{customerId}/add_product/{productId}")
    public void addProductToCustomerCart(@PathVariable Long customerId,
    @PathVariable Long productId)
    {
        service.addProductToCustomersCart(customerId, productId);
    }

@DeleteMapping("{customerId}/remove_product/{productId}")
    public void removeProductFromCustomerCart(
            @PathVariable Long customerId, @PathVariable Long productId)
    {
        service.removeProductFromCustomersCart(customerId, productId);
    }

    @DeleteMapping("{customerId}/clear_cart")
    public void clearCustomerCart(@PathVariable long customerId) {
        service.clearCustomersCart(customerId);
    }


    }


