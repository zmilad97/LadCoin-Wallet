package com.github.zmilad97.restfulblockchainwallet.Module.Transaction;

import java.util.HashMap;

public class TransactionInput {
    private String previousTransactionHash;
    private String indexReferenced;
    private HashMap<String, Object> scriptSignature; //proves that i own the pubKeyScript in trxOutput contains Signature(privateKey,pubKey)

    public String getPreviousTransactionHash() {
        return previousTransactionHash;
    }

    public void setPreviousTransactionHash(String previousTransactionHash) {
        this.previousTransactionHash = previousTransactionHash;
    }

    public String getIndexReferenced() {
        return indexReferenced;
    }

    public void setIndexReferenced(String indexReferenced) {
        this.indexReferenced = indexReferenced;
    }

    public HashMap<String, Object> getScriptSignature() {
        return scriptSignature;
    }

    public void setScriptSignature(HashMap<String, Object> scriptSignature) {
        this.scriptSignature = scriptSignature;
    }
}
