package com.example.truck.business.repository;

import java.time.LocalDateTime;
import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.truck.business.repository.dictionary.Dictionaries.ORDER_STATUS_CD;
import com.example.truck.business.repository.entity.OrderIssue;
import com.example.truck.business.repository.entity.OrderIssue_;

@Repository
public interface OrderIssueRepository extends JpaRepository<OrderIssue, Long>, JpaSpecificationExecutor<OrderIssue> {

	static Specification<OrderIssue> findAllCancelledOrCompletedOrders() {
		return (root, cq, cb) -> root.get(OrderIssue_.statusCd).in(ORDER_STATUS_CD.CANCELLED, ORDER_STATUS_CD.COMPLETED);
	}

	static Specification<OrderIssue> findAllNonCancelledOrCompletedOrders() {
		return (root, cq, cb) -> root.get(OrderIssue_.statusCd).in(ORDER_STATUS_CD.CANCELLED, ORDER_STATUS_CD.COMPLETED).not();
	}

	static Specification<OrderIssue> findAllAvailableOrders() {
		return (root, cq, cb) ->
				cb.and(
						cb.isNull(root.join(OrderIssue_.driver, JoinType.LEFT)),
						cb.equal(root.get(OrderIssue_.statusCd), ORDER_STATUS_CD.NEW),
						cb.greaterThanOrEqualTo(root.get(OrderIssue_.completionDate), LocalDateTime.now())
				);
	}

}
