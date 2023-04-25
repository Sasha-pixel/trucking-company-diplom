package com.example.truck.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.truck.business.repository.entity.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long>, JpaSpecificationExecutor<Driver> {

}
