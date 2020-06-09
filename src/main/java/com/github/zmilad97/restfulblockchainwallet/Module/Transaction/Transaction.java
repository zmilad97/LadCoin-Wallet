package com.github.zmilad97.restfulblockchainwallet.Module.Transaction;


import java.util.HashMap;

public class Transaction {
    private String transactionId;  //TODO : need to fix this class
    private TransactionInput TransactionInput;
    private TransactionOutput TransactionOutput;
    private String transactionHash;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionInput getTransactionInput() {
        return TransactionInput;
    }

    public void setTransactionInput(String previousTransactionHash, int indexReferenced, HashMap<String, String> scriptSignature) {
        this.TransactionInput.setPreviousTransactionHash(previousTransactionHash);
        this.TransactionInput.setIndexReferenced(indexReferenced);
        this.TransactionInput.setScriptSignature(scriptSignature);

    }

    public TransactionOutput getTransactionOutput() {
        return TransactionOutput;
    }

    public void setTransactionOutput(double amount, String publicKey) {
        this.TransactionOutput.setAmount(amount);
        this.TransactionOutput.setPublicKeyScript(publicKey);
    }

    public String getTransactionHash() {   //TODO : Think About This Method
        return this.transactionHash;
    }

}
