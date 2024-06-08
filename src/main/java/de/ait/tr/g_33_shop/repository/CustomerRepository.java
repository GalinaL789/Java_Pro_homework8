package de.ait.tr.g_33_shop.repository;

import de.ait.tr.g_33_shop.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}

