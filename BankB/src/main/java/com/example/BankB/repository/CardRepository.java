package com.example.BankB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BankB.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{

}
