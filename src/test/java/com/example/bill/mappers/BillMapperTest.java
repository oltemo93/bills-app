package com.example.bill.mappers;

import com.example.bill.repositories.entities.CustomerEntity;
import com.example.bill.repositories.entities.ElectricityBillEntity;
import com.example.bill.services.Customer;
import com.example.bill.services.electricity.ElectricityBill;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

class BillMapperTest {


    private final BillMapper mapper = Mappers.getMapper(BillMapper.class);

    @Test
    void testBillEntityToBill() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(123L);
        customerEntity.setFirstName("John");
        customerEntity.setLastName("Doe");

        ElectricityBillEntity entity = new ElectricityBillEntity();
        entity.setId(456L);
        entity.setCustomer(customerEntity);
        entity.setBalance(BigDecimal.valueOf(100.50));

        ElectricityBill actualBill = mapper.billEntityToBill(entity);

        Customer expectedCustomer = new Customer();
        expectedCustomer.setCustomerId(123L);
        expectedCustomer.setFirstName("John");
        expectedCustomer.setLastName("Doe");

        ElectricityBill expectedBill = new ElectricityBill();
        expectedBill.setBillId(456L);
        expectedBill.setBalance(BigDecimal.valueOf(100.5));
        expectedBill.setCustomer(expectedCustomer);
        assertThat("Bill is the expected", actualBill, samePropertyValuesAs(expectedBill, "customer"));
        assertThat("Customer is the expected", actualBill.getCustomer(), samePropertyValuesAs(expectedCustomer));
    }

    @Test
    void testBillDtoToBillEntity() {
        Customer customer = new Customer();
        customer.setCustomerId(123L);

        ElectricityBill bill = new ElectricityBill();
        bill.setBillId(456L);
        bill.setCustomer(customer);
        bill.setBalance(BigDecimal.valueOf(100.50));

        ElectricityBillEntity actualElectricityBillEntity = mapper.billDtoToBillEntity(bill);

        CustomerEntity expectedCustomerEntity = new CustomerEntity();
        expectedCustomerEntity.setId(123L);

        ElectricityBillEntity expectedBillEntity = new ElectricityBillEntity();
        expectedBillEntity.setBalance(BigDecimal.valueOf(100.5));
        expectedBillEntity.setCustomer(expectedCustomerEntity);
        assertThat("Bill is the expected", actualElectricityBillEntity, samePropertyValuesAs(expectedBillEntity, "customer"));
        assertThat("Customer is the expected", actualElectricityBillEntity.getCustomer(), samePropertyValuesAs(expectedCustomerEntity));
    }

}