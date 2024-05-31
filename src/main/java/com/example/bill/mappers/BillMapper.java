package com.example.bill.mappers;

import com.example.bill.repositories.entities.ElectricityBillEntity;
import com.example.bill.services.ElectricityBill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BillMapper {

    @Mapping(target = "billId", source = "id")
    @Mapping(target = "customer.customerId", source = "customer.id")
    @Mapping(target = "customer.firstName", source = "customer.firstName")
    @Mapping(target = "customer.lastName", source = "customer.lastName")
    @Mapping(target = "balance", source = "balance")
     ElectricityBill billEntityToBill(ElectricityBillEntity electricityBillEntity);

}
