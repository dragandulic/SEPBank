package com.example.BankB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BankB.model.PCCrequest;

@Repository
public interface PCCrequestRepository extends JpaRepository<PCCrequest, Long>{

}
