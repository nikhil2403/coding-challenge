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
import java.time.Instant;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;

@Service
public class AsyncEvict {
    @Autowired
    private BlockingDeque<AbstractMap.SimpleEntry<Long,Long>> evictBuffer;
    @Autowired
    private ConcurrentMap<Long, List<Transaction>> primaryMap;
    @Autowired
    private ConcurrentNavigableMap<BigDecimal,Set<Long>> sortedMap;
    @Autowired
    private TransactionStats transactionStats;
    @Autowired
    BlockingDeque<Transaction> inputBuffer;

   // @Async
//    public void handleAddToDS(){
//        while(true){
//            Transaction transaction = inputBuffer.peek();
//            if (transaction==null){
//                continue;
//            }
//            evictBuffer.add(new AbstractMap.SimpleEntry<>(transaction.getTimestampLong()+60000,transaction.getTimestampLong()));
//            primaryMap.putIfAbsent(transaction.getTimestampLong(),new ArrayList<>());
//            primaryMap.computeIfPresent(transaction.getTimestampLong(),(k, v)-> {
//                v.add(transaction);
//                return v;
//            });
//            sortedMap.putIfAbsent(transaction.getAmount(),new HashSet<>());
//            sortedMap.computeIfPresent(transaction.getAmount(),(k, v)->{
//                v.add(transaction.getTimestampLong());
//                return v;
//            });
//            transactionStats.setCount(transactionStats.getCount()+1);
//            transactionStats.setAverage( (transaction.getAmount()/transactionStats.getCount())+ transactionStats.getSum()/transactionStats.getCount());
//            transactionStats.setSum(transactionStats.getSum()+transaction.getAmount());
//            handleRemoveFromlDS();
//        }
//    }


    @Async("asyncEvictExecutor")
    // @Scheduled(fixedRate = 50)
    public void handleRemoveFromDS() throws InterruptedException {
        while (true) {
            AbstractMap.SimpleEntry<Long, Long> entry = evictBuffer.peek();
            if (entry!=null && entry.getKey() <= Instant.now().toEpochMilli()) {
                evictBuffer.take();
                List<Transaction> transactionList = primaryMap.remove(entry.getValue());
                if (transactionList==null){
                    continue;
                    //return;
                }
                for (Transaction transaction : transactionList) {
                    transactionStats.setSum(transactionStats.getSum().subtract(transaction.getAmount()));
                    transactionStats.setCount(transactionStats.getCount()-1);
                    transactionStats.setAverage(transactionStats.getCount()==0? BigDecimalRoundTwoHalfUp.valueOf(0) :transactionStats.getSum().divide(BigDecimal.valueOf(transactionStats.getCount()),new MathContext(10, RoundingMode.HALF_UP)));
                    transactionStats.setMax(transactionStats.getCount()==0? BigDecimalRoundTwoHalfUp.valueOf(0) :transactionStats.getMax());
                    transactionStats.setMin(transactionStats.getCount()==0? BigDecimalRoundTwoHalfUp.valueOf(0) :transactionStats.getMin());
                    Set<Long> timeSet = sortedMap.get(transaction.getAmount());
                    if (timeSet != null) {
                        timeSet.remove(transaction.getTimestampLong());
                        if (timeSet.size() == 0) {
                            sortedMap.remove(transaction.getAmount());
                        }
                    }
                }
            }
        }
    }
}
