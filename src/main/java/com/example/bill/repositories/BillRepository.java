package com.example.bill.repositories;

import com.example.bill.repositories.entities.ElectricityBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<ElectricityBillEntity, Long> {

     Optional<ElectricityBillEntity> findByCustomerId(Long customerId);

}
