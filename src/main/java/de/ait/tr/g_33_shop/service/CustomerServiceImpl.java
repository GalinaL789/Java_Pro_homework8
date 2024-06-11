package de.ait.tr.g_33_shop.service;

import de.ait.tr.g_33_shop.domain.dto.CustomerDto;
import de.ait.tr.g_33_shop.domain.entity.Customer;
import de.ait.tr.g_33_shop.repository.CustomerRepository;
import de.ait.tr.g_33_shop.service.interfaces.CustomerService;
import de.ait.tr.g_33_shop.service.mapping.CustomerMappingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMappingService mappingService;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMappingService mappingService) {
        this.customerRepository = customerRepository;
        this.mappingService = mappingService;
    }

    @Override
    public CustomerDto save(CustomerDto dto) {
        Customer entity = mappingService.mapDtoToEntity(dto);
        customerRepository.save(entity);
        return mappingService.mapEntityToDto(entity);
    }

    //    @Override
//    public List<Customer> getAllActiveCustomers() {
//        List<Customer> activeCustomers = new ArrayList<>();
//        List<Customer> allCustomers = customerRepository.findAll();
//        for (Customer customer : allCustomers) {
//            if (customer.isActive()) {
//                activeCustomers.add(customer);
//            }
//        }
//        return activeCustomers;
//    }
    @Override
    public List<CustomerDto> getAllActiveCustomers() {
        return customerRepository.findAll()
                .stream()
                .filter(Customer::isActive)
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    //        @Override
//    public Customer getById(Long id) {
//        return customerRepository.findById(id).orElse(null);
//
//    }
    @Override
    public CustomerDto getById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null && customer.isActive()) {
            return mappingService.mapEntityToDto(customer);
        }
        return null;
    }

    @Override
    public CustomerDto update(CustomerDto dto) {
        Customer entity = mappingService.mapDtoToEntity(dto);
        customerRepository.save(entity);
        return mappingService.mapEntityToDto(entity);
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public void restoreById(Long id) {
        System.out.println("Restore Customer: " + id);
    }

    @Override
    public long getActiveCustomersNumber() {
        return 0;
    }
}
