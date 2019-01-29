package com.example.BankB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.BankB.model.Merchant;

@Repository
public interface BankRepository extends JpaRepository<Merchant, Long>{
	
	Merchant findByIdEquals(Long id);
	
	
	@Query("select ct from Merchant ct where (ct.merchant_id) = (:id)")
	Merchant findByMerchantIdEquals(@Param("id")String id);

}
