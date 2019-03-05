package com.n26.nikhil.stats.domain;

import com.n26.nikhil.stats.utils.BigDecimalRoundTwoHalfUp;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TransactionStats {
    private  volatile BigDecimal sum;
    private  volatile BigDecimal average;
    private volatile BigDecimal max;
    private volatile BigDecimal min;
    private  volatile long count;

    public BigDecimal getSum() {
        return sum;
    }

    public synchronized   void  setSum(BigDecimal sum) {
        this.sum = sum;
       this.sum = this.sum.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getAverage() {
        return average;
    }

    public synchronized void setAverage(BigDecimal average) {
        this.average = average;
        this.average = this.average.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getMax() {
        return max;
    }

    public synchronized void setMax(BigDecimal max) {
        this.max = max;
        this.max = this.max.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getMin() {
        return min;
    }

    public synchronized void setMin(BigDecimal min) {
        this.min = min;
       this.min = this.min.setScale(2, RoundingMode.HALF_UP);
    }

    public long getCount() {
        return count;
    }

    public  synchronized  void setCount(long count) {
        this.count = count;
    }

    public TransactionStats(BigDecimal sum, BigDecimal average, BigDecimal max, BigDecimal min, long count) {
        this.sum = sum;
        this.average = average;
        this.max = max;
        this.min = min;
        this.count = count;
    }
    public TransactionStats(){
    init();
    }

    public void init() {
        sum = new BigDecimal("0");
        average = new BigDecimal("0");
        max = new BigDecimal("0");
        min = new BigDecimal("0");
        count = 0;
    }
    public void setScale(){
        sum = sum.setScale(2, RoundingMode.HALF_UP);
        average = average.setScale(2, RoundingMode.HALF_UP);
        max = max.setScale(2, RoundingMode.HALF_UP);
        min = min.setScale(2, RoundingMode.HALF_UP);
    }
}
