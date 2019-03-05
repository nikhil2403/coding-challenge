package com.n26.nikhil.stats.service;

import com.n26.nikhil.stats.domain.Transaction;
import com.n26.nikhil.stats.domain.TransactionStats;
import org.springframework.http.HttpStatus;

public interface TransactionService {
     HttpStatus submitTransaction(Transaction transaction);
     TransactionStats getStats();

    HttpStatus deleteAllTransactions();
}
