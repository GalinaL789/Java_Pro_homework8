package de.ait.tr.g_33_shop.repository;

import de.ait.tr.g_33_shop.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByTitle(String title);
}
