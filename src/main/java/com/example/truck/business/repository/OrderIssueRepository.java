package com.example.truck.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.truck.business.repository.entity.OrderIssue;

@Repository
public interface OrderIssueRepository extends JpaRepository<OrderIssue, Long>, JpaSpecificationExecutor<OrderIssue> {

}
