package com.example.BankB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BankB.model.PCCresponse;

@Repository
public interface PCCresponseRepository extends JpaRepository<PCCresponse, Long>{

}
