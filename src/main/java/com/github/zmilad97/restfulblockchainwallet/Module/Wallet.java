package com.github.zmilad97.restfulblockchainwallet.Module;

import com.github.zmilad97.restfulblockchainwallet.Module.Transaction.Transaction;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Wallet {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String signature;
    private double balance;
    private String lastTransactionHash ;
    private Transaction UTXOs ;

    public Wallet(){

    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getLastTransactionHash() {
        return lastTransactionHash;
    }

    public void setLastTransactionHash(String lastTransactionHash) {
        this.lastTransactionHash = lastTransactionHash;
    }

    public Transaction getUTXOs() {
        return UTXOs;
    }

    public void setUTXOs(Transaction UTXOs) {
        this.UTXOs = UTXOs;
    }

}
