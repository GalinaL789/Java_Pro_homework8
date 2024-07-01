package de.ait.tr.g_33_shop.repository;

import de.ait.tr.g_33_shop.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByTitle(String title);
}