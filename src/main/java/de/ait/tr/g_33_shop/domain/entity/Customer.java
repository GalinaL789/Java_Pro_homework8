package de.ait.tr.g_33_shop.domain.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Customer {
    private Long id;
    private String name;
    private boolean active;


    @Override
    public String toString() {
        return String.format("Product: id - %d, name- %s, active - %s",
                id, name, active ? "yes" : "no");
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return active == customer.active && Objects.equals(id, customer.id) && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, active);
    }

}
