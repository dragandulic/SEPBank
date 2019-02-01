package com.example.Bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Bank.model.PCCrequest;

@Repository
public interface PCCrequestRepository extends JpaRepository<PCCrequest, Long> {

	
	
}
