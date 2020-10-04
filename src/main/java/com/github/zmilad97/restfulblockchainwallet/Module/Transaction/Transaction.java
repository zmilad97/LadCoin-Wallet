package com.github.zmilad97.restfulblockchainwallet.Module.Transaction;


public class Transaction {

    private String transactionId;
    private TransactionInput TransactionInput;
    private TransactionOutput TransactionOutput;
    private String transactionHash;


    public Transaction(String transactionId, TransactionInput transactionInput, TransactionOutput transactionOutput) {
        this.transactionId = transactionId;
        TransactionInput = transactionInput;
        TransactionOutput = transactionOutput;
    }

    public Transaction(){

    }


    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionInput getTransactionInput() {
        return TransactionInput;
    }

    public void setTransactionInput(TransactionInput transactionInput) {
        this.TransactionInput = transactionInput;
    }

    public TransactionOutput getTransactionOutput() {
        return TransactionOutput;
    }

    public void setTransactionOutput(TransactionOutput transactionOutput) {
        this.TransactionOutput = transactionOutput;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getTransactionHash() {

        return this.transactionHash;
    }
}
