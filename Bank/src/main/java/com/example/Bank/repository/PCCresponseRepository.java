package com.example.Bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Bank.model.PCCresponse;

@Repository
public interface PCCresponseRepository extends JpaRepository<PCCresponse, Long>{

}
