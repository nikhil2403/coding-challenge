package com.n26.nikhil.stats.service.impl;

import com.n26.nikhil.stats.domain.Transaction;
import com.n26.nikhil.stats.domain.TransactionStats;
import com.n26.nikhil.stats.utils.BigDecimalRoundTwoHalfUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;

@Service
public class AsyncAdd {

    @Autowired
    private BlockingDeque<AbstractMap.SimpleEntry<Long,Long>> evictBuffer;
    @Autowired
    private ConcurrentMap<Long, List<Transaction>> primaryMap;
    @Autowired
    private ConcurrentNavigableMap<BigDecimal, Set<Long>> sortedMap;
    @Autowired
    private TransactionStats transactionStats;
    @Autowired
    BlockingDeque<Transaction> inputBuffer;
    @Autowired
    AsyncEvict asyncEvict;

    @Async
    public void handleAddToDS() throws InterruptedException {
        while (true) {
            Transaction transaction = inputBuffer.peek();
            if (transaction == null) {
                continue;
                //return;
            }
            inputBuffer.take();
            evictBuffer.add(new AbstractMap.SimpleEntry<>(transaction.getTimestampLong() + 60000, transaction.getTimestampLong()));
            primaryMap.putIfAbsent(transaction.getTimestampLong(), new ArrayList<>());
            primaryMap.computeIfPresent(transaction.getTimestampLong(), (k, v) -> {
                v.add(transaction);
                return v;
            });
            sortedMap.putIfAbsent(transaction.getAmount(), new HashSet<>());
            sortedMap.computeIfPresent(transaction.getAmount(), (k, v) -> {
                v.add(transaction.getTimestampLong());
                return v;
            });
            transactionStats.setCount(transactionStats.getCount() + 1);
            transactionStats.setAverage((transaction.getAmount().divide(BigDecimal.valueOf(transactionStats.getCount()),new MathContext(10, RoundingMode.HALF_UP))).add(transactionStats.getSum().divide(BigDecimal.valueOf(transactionStats.getCount()),new MathContext(10, RoundingMode.HALF_UP))) );
            transactionStats.setSum(transactionStats.getSum().add(transaction.getAmount()));
       }
    }
}
