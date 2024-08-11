package com.transfer.account.repo;

import com.transfer.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = """
     SELECT a.balance
     FROM Account a
     WHERE a.accountNumber like :accountNumber
     """)
    Optional<BigDecimal> getAccountCurrentBalanceByAccountNumber(String accountNumber);

    Optional<Account> getAccountByAccountNumber(String accountNumber);

   Optional<Account> findAccountByAccountNumber(String accountNumber);
}
