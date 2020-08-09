package com.github.zmilad97.restfulblockchainwallet.Module.Transaction;

import java.util.HashMap;
//TODO : This Class Seems Useless
public class TRX {
    private HashMap<String,String> source ;         //Signature,message = hash pubKey
    private String destination ;                    // public id
    private double amount ;

    public TRX(HashMap<String, String> source, String destination, double amount) {
        this.source = source;
        this.destination = destination;
        this.amount = amount;
    }

    public HashMap<String, String> getSource() {
        return source;
    }

    public void setSource(HashMap<String, String> source) {
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
