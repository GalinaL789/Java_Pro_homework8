package de.ait.tr.g_33_shop.domain.dto;

import java.util.Objects;


public class ProductSupplyDto {
    private Long id;
    private String Title;
    private int quantity;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return Title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSupplyDto that = (ProductSupplyDto) o;
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(Title, that.Title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Title, quantity);
    }
    @Override
    public String toString() {
        return String.format("Product: id - %d, title -%s, quantity -%d",
                id, Title, quantity);
    }
}
