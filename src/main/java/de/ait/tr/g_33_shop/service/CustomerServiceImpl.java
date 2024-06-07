package de.ait.tr.g_33_shop.service;

import de.ait.tr.g_33_shop.domain.entity.Customer;
import de.ait.tr.g_33_shop.service.interfaces.CustomerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService
{

    @Override
    public Customer save(Customer customer) {
        //TODO обраьтьбся к репозиторию
        customer.setName("Vasya");
        return customer;
    }

    @Override
    public List<Customer> getAllActiveCustomers() {
        List<Customer> customers = new ArrayList<Customer>();
        Customer customer = new Customer();
        customer.setActive(true);
        customer.setName("Kolya");
        customers.add(customer);
        return customers;
    }

    @Override
    public Customer getById(Long id) {
        return null;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteByName(String title) {

    }

    @Override
    public void restoreById(Long id) {
        System.out.println("Restore Customer: "+id);
    }

    @Override
    public long getActiveCustomersNumber() {
        return 0;
    }

    @Override
    public BigDecimal getTotalCostOfCustomersProducts(Long customerId) {

        return new BigDecimal(36.6);
    }


    @Override
    public BigDecimal getAverageCostOfCustomersProducts(Long customerId) {
        return null;
    }

    @Override
    public void addProductToCustomersCart(Long customerId, Long productId) {

    }

    @Override
    public void removeProductFromCustomersCart(Long customerId, Long productId) {

    }

    @Override
    public void clearCustomersCart(Long customerId) {

    }

}