package com.n26.nikhil.stats.service.impl;

import com.n26.nikhil.stats.domain.Transaction;
import com.n26.nikhil.stats.domain.TransactionStats;
import com.n26.nikhil.stats.service.TransactionService;
import com.n26.nikhil.stats.utils.BigDecimalRoundTwoHalfUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    BlockingDeque<Transaction> inputBuffer;
    @Autowired
    BlockingDeque<AbstractMap.SimpleEntry<Long,Long>> evictBuffer;
    @Autowired
    private ConcurrentMap<Long, List<Transaction>> primaryMap;
    @Autowired
    private ConcurrentNavigableMap<BigDecimal, Set<Long>> sortedMap;
    @Autowired
    private TransactionStats transactionStats;

    @Override
    public HttpStatus submitTransaction(Transaction transaction) {
        boolean timeGreaterThan60SecondsAgo = isTimeGreaterThan60SecondsAgo(transaction);
        if (timeGreaterThan60SecondsAgo) {
            return  HttpStatus.NO_CONTENT;
        }
        else if(isTimeLessThanTimeStamp(transaction)){
            return HttpStatus.UNPROCESSABLE_ENTITY;
        }
        inputBuffer.add(transaction);
        return HttpStatus.CREATED;
    }

    private boolean isTimeGreaterThan60SecondsAgo(Transaction transaction) {

        return Instant.now().minusSeconds(60).toEpochMilli() > transaction.getTimestampLong();
    }

    private boolean isTimeLessThanTimeStamp(Transaction transaction) {

        return Instant.now().toEpochMilli() < transaction.getTimestampLong();
    }



    @Override
    public TransactionStats getStats() {
        if (sortedMap.size()>0){
            transactionStats.setMax(sortedMap.lastKey());
            transactionStats.setMin(sortedMap.firstKey());
        }
        transactionStats.setScale();
        return transactionStats;
    }

    @Override
    public HttpStatus deleteAllTransactions() {
        this.sortedMap.clear();
        this.primaryMap.clear();
        this.inputBuffer.clear();
        this.evictBuffer.clear();
        this.transactionStats.init();

        return HttpStatus.NO_CONTENT;

    }
}
