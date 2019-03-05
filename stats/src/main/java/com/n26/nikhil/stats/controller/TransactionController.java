package com.n26.nikhil.stats.controller;

import com.n26.nikhil.stats.domain.Transaction;
import com.n26.nikhil.stats.domain.TransactionStats;
import com.n26.nikhil.stats.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/statistics")
    public TransactionStats getStats() {
        return transactionService.getStats();
    }

    @PostMapping("/transactions")
    public ResponseEntity<?> submitTransaction(@RequestBody Transaction transaction){
       // transaction.setTimestampLong(System.currentTimeMillis());
        HttpStatus httpStatus = transactionService.submitTransaction(transaction);
      return   ResponseEntity.status(httpStatus.value()).body(null);

    }

    @DeleteMapping("/transactions")
    public ResponseEntity<?> deleteAllTransactions(){
        HttpStatus httpStatus = transactionService.deleteAllTransactions();
        return   ResponseEntity.status(httpStatus.value()).body(null);
    }

}
