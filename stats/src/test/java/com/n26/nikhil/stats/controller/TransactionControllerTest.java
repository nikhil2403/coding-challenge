package com.n26.nikhil.stats.controller;

import com.n26.nikhil.stats.StatsApplication;
import com.n26.nikhil.stats.domain.Transaction;
import com.n26.nikhil.stats.domain.TransactionStats;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StatsApplication.class},webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getStats() {
        ResponseEntity<TransactionStats> stats = restTemplate.getForEntity("/statistics", TransactionStats.class);
        assertNotNull(stats);
    }

    @Test
    public void submitTransaction() {
        String thisTime =  "2018-07-17T09:59:51.312Z";
        ResponseEntity<HttpStatus> status = restTemplate.postForEntity("/transactions", new Transaction(BigDecimal.valueOf(14.0), thisTime), HttpStatus.class);
        assertNotNull(status);
        assertEquals(status.getBody(),HttpStatus.CREATED);
        thisTime =  "2018-07-17T09:57:51.312Z";
       status= restTemplate.postForEntity("/transactions", new Transaction(BigDecimal.valueOf(14.0), thisTime), HttpStatus.class);
        assertNotNull(status);
        assertEquals(status.getBody(),HttpStatus.NO_CONTENT);
    }

    @Test
    public void testStatsWithData() throws InterruptedException {
       restTemplate.delete("/transactions");
        BigDecimal amount = BigDecimal.valueOf(0);
        String thisTime = Instant.now().toString();
        for (int i = 0; i < 5; i++) {
            amount = amount.add(BigDecimal.valueOf(1));
            restTemplate.postForEntity("/transactions", new Transaction(amount, thisTime), HttpStatus.class);
        }
        ResponseEntity<TransactionStats> transactionStatsResponse = restTemplate.getForEntity("/statistics", TransactionStats.class);
        assertNotNull(transactionStatsResponse);
        TransactionStats transactionStats = transactionStatsResponse.getBody();
        assertEquals(BigDecimal.valueOf(15).setScale(2, RoundingMode.HALF_UP),transactionStats.getSum());
        assertEquals(BigDecimal.valueOf(3).setScale(2, RoundingMode.HALF_UP),transactionStats.getAverage());
        assertEquals(5,transactionStats.getCount());
        assertEquals(BigDecimal.valueOf(5).setScale(2, RoundingMode.HALF_UP),transactionStats.getMax());
        assertEquals(BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),transactionStats.getMin());
     //clear out pending transactions
        restTemplate.delete("/transactions");
        transactionStatsResponse = restTemplate.getForEntity("/statistics", TransactionStats.class);
        assertNotNull(transactionStatsResponse);

         transactionStats = transactionStatsResponse.getBody();
        assertEquals(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP),transactionStats.getSum());
        assertEquals(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP),transactionStats.getAverage());
        assertEquals(0,transactionStats.getCount());
        assertEquals(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP),transactionStats.getMax());
        assertEquals(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP),transactionStats.getMin());

        //re test for 5 more requests
         thisTime =  Instant.now().toString();
        amount= BigDecimal.valueOf(0);
        for (int i = 0; i < 5; i++) {
            amount = amount.add(BigDecimal.valueOf(1));
            restTemplate.postForEntity("/transactions", new Transaction(amount, thisTime), HttpStatus.class);
        }
         transactionStatsResponse = restTemplate.getForEntity("/statistics", TransactionStats.class);
        assertNotNull(transactionStatsResponse);
         transactionStats = transactionStatsResponse.getBody();
        assertEquals(BigDecimal.valueOf(15).setScale(2, RoundingMode.HALF_UP),transactionStats.getSum());
        assertEquals(BigDecimal.valueOf(3).setScale(2, RoundingMode.HALF_UP),transactionStats.getAverage());
        assertEquals(5,transactionStats.getCount());
        assertEquals(BigDecimal.valueOf(5).setScale(2, RoundingMode.HALF_UP),transactionStats.getMax());
        assertEquals(BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),transactionStats.getMin());

       restTemplate.delete("/transactions");

    }

    @Test
    public void testDelete(){
        ResponseEntity<HttpStatus> exchange = restTemplate.exchange("/transactions", HttpMethod.DELETE, null, HttpStatus.class);
        assertNotNull(exchange);
        assertEquals(204,exchange.getStatusCode().value());
    }

}