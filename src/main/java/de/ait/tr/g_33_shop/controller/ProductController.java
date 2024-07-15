package de.ait.tr.g_33_shop.controller;

import de.ait.tr.g_33_shop.domain.dto.ProductDto;
import de.ait.tr.g_33_shop.domain.dto.ProductSupplyDto;
import de.ait.tr.g_33_shop.domain.entity.Product;
import de.ait.tr.g_33_shop.exception_handling.Response;
import de.ait.tr.g_33_shop.exception_handling.exceptions.FirstTestException;
import de.ait.tr.g_33_shop.service.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product controller", description = "Controller for various operations with Products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

// CRUD - Create (POST), Read (GET), Update (PUT), Delete (DELETE)

    // Create: POST -> localhost:8080/products

    @PostMapping
    public ProductDto save(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Instance of a Product")
            ProductDto product
    ) {
        return service.save(product);
    }

    // Read: GET -> localhost:8080/products?id=3

    @Operation(
            summary = "Get one or all products",
            description = "Getting one or all products that exist in the database"
    )
    @GetMapping
    public ProductDto getById(
            @RequestParam
            @Parameter(description = "Product unique identifier")
            Long id
    ) {

        return service.getById(id);
    }
//    @GetMapping
//    public List<ProductDto> get(
//            @RequestParam(required = false)
//            @Parameter(description = "Product unique identifier")
//            Long id
//    ) {
//        if (id == null) {
//            return service.getAllActiveProducts();
//        } else {
//            ProductDto product = service.getById(id);
//            return product == null ? null : List.of(product);
//        }
//    }

   @GetMapping("/all")
    public List<ProductDto> getAll() {
        return service.getAllActiveProducts();
    }

    // Update: PUT -> localhost:8080/products

    @PutMapping
    public ProductDto update(@RequestBody ProductDto product) {
        return service.update(product);
    }

    // Delete: DELETE -> localhost:8080/products?id=3

    @DeleteMapping
    public void delete(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String title
    ) {
        if (id != null) {
            service.deleteById(id);
        } else if (title != null) {
            service.deleteByTitle(title);
        }
    }

    @PutMapping("/restore")
    public void restore(@RequestParam Long id) {
        service.restoreById(id);
    }

    @GetMapping("/quantity")
    public long getProductsQuantity() {
        return service.getAllActiveProductsQuantity();
    }

    @GetMapping("/total-price")
    public BigDecimal getTotalPrice() {
        return service.getAllActiveProductsTotalPrice();
    }

    @GetMapping("/average-price")
    public BigDecimal getAveragePrice() {
        return service.getAllActiveProductsAveragePrice();
    }
    //1
    @ExceptionHandler(FirstTestException.class)
    public Response handleException(FirstTestException e){
        return new Response(e.getMessage());
    }
}
