package com.example.Bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Bank.model.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{

}
