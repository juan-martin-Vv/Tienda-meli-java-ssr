package com.jmvv.MpShop.config;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.jmvv.MpShop.models.phoneModel;
import com.mercadopago.resources.preference.PreferenceItem;

@Mapper
public interface MapperStruct {
    MapperStruct INSTANCE = Mappers.getMapper( MapperStruct.class);
    
    
    @Mapping(source ="pictureUrl" , target = "img")
    @Mapping(source = "unitPrice", target = "price")
    @Mapping(source = "quantity", target = "unit")
    phoneModel toRecord(PreferenceItem item);
    
}
