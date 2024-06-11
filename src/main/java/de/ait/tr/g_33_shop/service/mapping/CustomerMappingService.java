package de.ait.tr.g_33_shop.service.mapping;

import de.ait.tr.g_33_shop.domain.dto.CustomerDto;
import de.ait.tr.g_33_shop.domain.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Customer mapDtoToEntity(CustomerDto dto);
    CustomerDto mapEntityToDto(Customer entity);

    // Ручной вариант маппинга
//    public Product mapDtoToEntity(ProductDto dto) {
//        Product entity = new Product();
//        entity.setTitle(dto.getTitle());
//        entity.setPrice(dto.getPrice());
//        entity.setActive(true);
//        return entity;
//    }
//
//    public ProductDto mapEntityToDto(Product entity) {
//        ProductDto dto = new ProductDto();
//        dto.setId(entity.getId());
//        dto.setTitle(entity.getTitle());
//        dto.setPrice(entity.getPrice());
//        return dto;
//    }
}

