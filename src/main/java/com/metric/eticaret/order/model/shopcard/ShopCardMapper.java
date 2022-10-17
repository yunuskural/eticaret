package com.metric.eticaret.order.model.shopcard;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopCardMapper {

    ShopCardMapper INSTANCE = Mappers.getMapper(ShopCardMapper.class);

    ShopCard toEntity(ShopCardDTO shopCardDTO);

    ShopCardDTO toDTO(ShopCard shopCard);
}
