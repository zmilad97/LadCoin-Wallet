package com.github.zmilad97.restfulblockchainwallet.Module.Transaction;


public class Transaction {
    private String transactionId;
    private String source;
    private String destination;
    private double amount;

    public Transaction(String source, String destination, double amount) {
        this.source = source;
        this.destination = destination;
        this.amount = amount;
    }

    public Transaction(){

    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
