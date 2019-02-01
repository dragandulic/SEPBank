package com.example.BankB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.BankB.model.Merchant;
import com.example.BankB.model.PCCrequest;

@Repository
public interface PCCrequestRepository extends JpaRepository<PCCrequest, Long>{
	
	
	@Query("select ct from PCCrequest ct where (ct.acquirer_order_id) = (:id)")
	PCCrequest findByAcquirerOrderIdEquals(@Param("id")Long id);

}
