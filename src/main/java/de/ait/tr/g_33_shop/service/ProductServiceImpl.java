package de.ait.tr.g_33_shop.service;

import de.ait.tr.g_33_shop.domain.entity.Product;
import de.ait.tr.g_33_shop.repository.ProductRepository;
import de.ait.tr.g_33_shop.service.interfaces.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        product.setId(null);
        product.setActive(true);
        return repository.save(product);
    }

    @Override
    public List<Product> getAllActiveProducts() {
        //List<Product> products = new ArrayList<>();
        return repository.findAll()
                .stream()
                .filter(Product::isActive)
                .toList();
    }

    @Override
    public Product getById(Long id) {
        Product product = repository.findById(id).orElse(null);
        if (product != null && product.isActive()) {
            return product;
        }
        return null;
    }
    @Override
    public Product update(Product product) {
        // Извлекаем текущий продукт из базы данных
        Product existingProduct = repository.findById(product.getId()).orElseThrow(() -> new RuntimeException("Product not found"));

        // Проверяем, что только поля price или active изменены
        if (!product.getTitle().equals(existingProduct.getTitle()))  {
            throw new RuntimeException("Only price or active can be changed");
        }
        // Обновляем существующий продукт только в случае, если изменения допустимы
        existingProduct.setPrice(product.getPrice());
        existingProduct.setActive(product.isActive());

        // Сохраняем изменения
        return repository.save(existingProduct);
    }


    @Override
    public void deleteById(Long id) {

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
