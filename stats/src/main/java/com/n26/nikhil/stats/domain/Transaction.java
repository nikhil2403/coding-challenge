package com.n26.nikhil.stats.domain;

import com.n26.nikhil.stats.exception.UnparsableException;
import com.n26.nikhil.stats.utils.BigDecimalRoundTwoHalfUp;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

public class Transaction {
    private BigDecimal amount;
    private Long timestampLong;
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
       try {
           this.timestamp = timestamp;
           Instant instant = Instant.parse(timestamp);
           this.timestampLong = instant.toEpochMilli();
       }catch (RuntimeException e){
           throw new UnparsableException(HttpStatus.UNPROCESSABLE_ENTITY);
       }
    }

    public Transaction(BigDecimal amount, String timestamp) {
        try {
            this.amount = amount;
            this.timestamp = timestamp;
            Instant instant = Instant.parse(timestamp);
            this.timestampLong = instant.toEpochMilli();
        } catch (RuntimeException e) {
            throw new UnparsableException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public Transaction() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
        this.amount=this.amount.setScale(2, RoundingMode.HALF_UP);
    }

    public Long getTimestampLong() {
        return timestampLong;
    }

    public void setTimestampLong(Long timestampLong) {

        this.timestampLong = timestampLong;
    }
}
