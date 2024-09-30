package com.absattarov.BankRestAPI.repositories;

import com.absattarov.BankRestAPI.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account getById(int id);
}
