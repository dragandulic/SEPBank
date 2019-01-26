package com.example.Bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Bank.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

}
