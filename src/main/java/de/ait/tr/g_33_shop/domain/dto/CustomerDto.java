package de.ait.tr.g_33_shop.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Schema(description = "Class that describes Product")
public class CustomerDto {

    @Schema(
            description = "Customer unique identifier",
            example = "777",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(description = "Customer name", example = "Kolya")
    private String name;

    @Schema(description = "Product price", example = "190.00")
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String title) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Customer DTO: id - %d, name - %s",
                id, name);
    }
}
