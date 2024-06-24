package de.ait.tr.g_33_shop.service;

import de.ait.tr.g_33_shop.domain.dto.ProductDto;
import de.ait.tr.g_33_shop.domain.entity.Product;
import de.ait.tr.g_33_shop.exception_handling.exceptions.FourthTestException;
import de.ait.tr.g_33_shop.exception_handling.exceptions.NotActiveProductsException;
import de.ait.tr.g_33_shop.exception_handling.exceptions.ProductNotFoundException;
import de.ait.tr.g_33_shop.repository.ProductRepository;
import de.ait.tr.g_33_shop.service.interfaces.ProductService;
import de.ait.tr.g_33_shop.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;
    private ProductMappingService mappingService;

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }


    @Override
    public ProductDto save(ProductDto dto) {
        Product entity = mappingService.mapDtoToEntity(dto);
        try {
            repository.save(entity);
        }
        catch (Exception e) {
            throw new FourthTestException(e.getMessage());
        }
        return mappingService.mapEntityToDto(entity);
    }

    @Override

    public List<ProductDto> getAllActiveProducts() {
        List<ProductDto> productDtos=repository.findAll()
                .stream()
                .filter(Product::isActive)
                .map(mappingService::mapEntityToDto)
                .toList();
        if(productDtos.size()==0) {
            throw new NotActiveProductsException("There is no active products");
        }

            return productDtos;
        }
    @Override
    public ProductDto getById(Long id) {
        Product product = repository.findById(id).orElse(null);

        if (product != null && product.isActive()) {
            return mappingService.mapEntityToDto(product);
        }

        throw new NotActiveProductsException("the product is not found");
    }


    @Override
    public ProductDto update(ProductDto productDto) {
        // Проверяем, существует ли продукт с данным ID
        Optional<Product> optionalProduct = repository.findById(productDto.getId());

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            // Обновляем поля продукта
            existingProduct.setPrice(productDto.getPrice());
            // Добавьте обновление других полей по мере необходимости

            // Сохраняем обновленный продукт в базу данных
            Product updatedProduct = repository.save(existingProduct);
            // Конвертируем обновленный продукт обратно в DTO и возвращаем его
            return convertToDto(updatedProduct);
        } else {
            // Логика для случая, когда продукт не найден
            throw new ProductNotFoundException("Product with ID " + productDto.getId() + " not found.");

        }
    }
    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setPrice(product.getPrice());
        // Добавьте установку других полей по мере необходимости
        return productDto;
    }

//    @Override
//    public ProductDto update(ProductDto product) {
//        return null;
//    }

//    @Override
//    public void deleteById(Long id) {
//
//    }
    @Override
    public void deleteById(Long id) {
        // Проверяем, существует ли продукт с данным ID
        Optional<Product> optionalProduct = repository.findById(id);

        if (optionalProduct.isPresent()) {
            // Удаляем продукт
            repository.deleteById(id);
        } else {
            // Логика для случая, когда продукт не найден
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
    }

    @Override
    public void deleteByTitle(String title) {

    }

    @Override
    public void restoreById(Long id) {

    }

    @Override
    public long getAllActiveProductsQuantity() {
        return 0;
    }

    @Override
    public BigDecimal getAllActiveProductsTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getAllActiveProductsAveragePrice() {
        return null;
    }
}
