package com.github.zmilad97.restfulblockchainwallet.Module.Transaction;

public class TransactionInput {
   /* private String previousTransactionHash;
    private int indexReferenced;
    private String scriptSignature; //proves that i own the pubKeyScript in trxOutput contains Signature(privateKey,pubKey)

    public String getPreviousTransactionHash() {
        return previousTransactionHash;
    }

    public int getIndexReferenced() {
        return indexReferenced;
    }

    public void setIndexReferenced(int indexReferenced) {
        this.indexReferenced = indexReferenced;
    }

    public String getScriptSignature() {
        return scriptSignature;
    }

    public void setScriptSignature(String scriptSignature) {
        this.scriptSignature = scriptSignature;
    }

    public void setPreviousTransactionHash(String previousTransactionHash) {
        this.previousTransactionHash = previousTransactionHash;
    }*/

    private String previousTransactionHash;
    private int indexReferenced;
    private String scriptSignature;

    public String getPreviousTransactionHash() {
        return previousTransactionHash;
    }

    public void setPreviousTransactionHash(String previousTransactionHash) {
        this.previousTransactionHash = previousTransactionHash;
    }

    public int getIndexReferenced() {
        return indexReferenced;
    }

    public void setIndexReferenced(int indexReferenced) {
        this.indexReferenced = indexReferenced;
    }

    public String getScriptSignature() {

        return scriptSignature;
    }

    public void setScriptSignature(String scriptSignature) {
        this.scriptSignature = scriptSignature;
    }

}
