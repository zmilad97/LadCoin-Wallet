package com.github.zmilad97.restfulblockchainwallet.Module.Transaction;


public class Transaction {

    private String transactionId;       //TODO need to fix this class
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

    public String getTransactionHash() {   //TODO : Think About This Method

        return this.transactionHash;
    }



    //    public void setTransactionInput(String previousTransactionHash, int indexReferenced, HashMap<String, String> scriptSignature) {
//        this.TransactionInput.setPreviousTransactionHash(previousTransactionHash);
//        this.TransactionInput.setIndexReferenced(indexReferenced);
//        this.TransactionInput.setScriptSignature(scriptSignature);
//
//    }

    /*public void setTransactionOutput(double amount, String publicKey) {
        this.TransactionOutput.setAmount(amount);
        this.TransactionOutput.setPublicKeyScript(publicKey);
    }*/
}
