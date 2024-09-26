package com.absattarov.BankRestAPI.repositories;

import com.absattarov.BankRestAPI.models.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {
    ExchangeRate findTopByOrderByCloseDateDesc();
}
