package com.example.Bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Bank.model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

	Request findByIdEquals(Long id);
	
}
