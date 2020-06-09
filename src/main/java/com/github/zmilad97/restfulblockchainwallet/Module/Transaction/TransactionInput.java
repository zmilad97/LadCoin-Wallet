package com.github.zmilad97.restfulblockchainwallet.Module.Transaction;

import java.util.HashMap;

public class TransactionInput {
    private String previousTransactionHash;
    private int indexReferenced;
    private HashMap<String, String> scriptSignature; //proves that i own the pubKeyScript in trxOutput contains Signature(privateKey,pubKey)

    public String getPreviousTransactionHash() {
        return previousTransactionHash;
    }

    public int getIndexReferenced() {
        return indexReferenced;
    }

    public void setIndexReferenced(int indexReferenced) {
        this.indexReferenced = indexReferenced;
    }

    public HashMap<String, String> getScriptSignature() {
        return scriptSignature;
    }

    public void setScriptSignature(HashMap<String, String> scriptSignature) {
        this.scriptSignature = scriptSignature;
    }

    public void setPreviousTransactionHash(String previousTransactionHash) {
        this.previousTransactionHash = previousTransactionHash;
    }
}
