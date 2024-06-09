package de.ait.tr.g_33_shop.service;

import de.ait.tr.g_33_shop.domain.entity.Customer;
import de.ait.tr.g_33_shop.repository.CustomerRepository;
import de.ait.tr.g_33_shop.service.interfaces.CustomerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService
{
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        return   customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllActiveCustomers() {
        List<Customer> activeCustomers = new ArrayList<>();
        List<Customer> allCustomers = customerRepository.findAll();
       for (Customer customer : allCustomers) {
           if (customer.isActive()) {
               activeCustomers.add(customer);
           }
       }
        return  activeCustomers;
    }

//    @Override
//    public Customer getById(Long id) {
//        return customerRepository.getOne(id);
//    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void restoreById(Long id) {
        System.out.println("Restore Customer: "+id);
    }

    @Override
    public long getActiveCustomersNumber() {
        return 0;
    }


}