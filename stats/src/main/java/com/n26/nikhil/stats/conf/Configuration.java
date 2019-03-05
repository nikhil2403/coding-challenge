package com.n26.nikhil.stats.conf;

import com.n26.nikhil.stats.domain.Transaction;
import com.n26.nikhil.stats.domain.TransactionStats;
import com.n26.nikhil.stats.utils.BigDecimalRoundTwoHalfUp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

@org.springframework.context.annotation.Configuration
@EnableAsync
public class Configuration  {
    @Bean
    public BlockingDeque<Transaction> inputBuffer(){

        return new LinkedBlockingDeque<>();
    }
    @Bean
    public BlockingDeque<AbstractMap.SimpleEntry<Long,Long>> evictBuffer(){
        return new LinkedBlockingDeque<>();
    }
    @Bean
    public ConcurrentMap<Long,List<Transaction>> getMap(){
      return new ConcurrentHashMap<>();
    }

    @Bean
    public ConcurrentNavigableMap<BigDecimal,Set<Long>> getSortedMap(){
        return new ConcurrentSkipListMap<>();
    }

    @Bean
    public TransactionStats getStats(){
        return new TransactionStats();
    }

    /**
     * The {@link Executor} instance to be used when processing async
     * method invocations.
     */
    @Bean
    @Primary
    public Executor getAsyncExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        executor.setQueueCapacity(500);
        executor.initialize();
        return executor;
    }
    @Bean
    public Executor asyncEvictExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        executor.setQueueCapacity(500);
        executor.initialize();
        return executor;
    }
}
