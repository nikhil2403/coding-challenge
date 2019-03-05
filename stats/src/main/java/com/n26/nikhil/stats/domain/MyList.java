package com.n26.nikhil.stats.domain;

import java.util.ArrayList;

public class MyList extends ArrayList<Transaction> {
    ArrayList<Transaction> transactions = new ArrayList<>();

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *                                  is negative
     */
    public MyList(int initialCapacity, ArrayList<Transaction> transactions) {
        super(initialCapacity);
        this.transactions = transactions;
    }
    public MyList(){
    }

    private double min;
   private double max;

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public boolean add(Transaction transaction){
       /* max=Math.max(max,transaction.getAmount());
        min=Math.min(min,transaction.getAmount());*/
        transactions.add(transaction);
        return true;
    }
}
