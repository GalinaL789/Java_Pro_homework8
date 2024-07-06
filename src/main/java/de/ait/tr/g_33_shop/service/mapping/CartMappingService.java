package de.ait.tr.g_33_shop.service.mapping;

import de.ait.tr.g_33_shop.domain.dto.CartDto;
import de.ait.tr.g_33_shop.domain.entity.Cart;
import de.ait.tr.g_33_shop.service.mapping.ProductMappingService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductMappingService.class)
public interface CartMappingService {

    @Mapping(target = "id", ignore = true)
    Cart mapDtoToEntity(CartDto dto);

    CartDto mapEntityToDto(Cart entity);
}
