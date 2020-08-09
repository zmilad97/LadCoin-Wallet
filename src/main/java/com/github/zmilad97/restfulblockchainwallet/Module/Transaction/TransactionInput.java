package com.github.zmilad97.restfulblockchainwallet.Module.Transaction;

import java.util.HashMap;

public class TransactionInput {

    private HashMap<Integer , String> previousTransactionHash;
    private HashMap<Integer,Integer> indexReferenced;
    private String pubKey;

    public HashMap<Integer, String> getPreviousTransactionHash() {
        return previousTransactionHash;
    }
    public void addPreviousTransactionHash(int i , String s){
        this.previousTransactionHash.put(i,s);
    }
    public void setPreviousTransactionHash(HashMap<Integer, String> previousTransactionHash) {
        this.previousTransactionHash = previousTransactionHash;
    }
    public HashMap<Integer, Integer> getIndexReferenced() {
        return indexReferenced;
    }

    public void addIndexReferenced(int i , int j){

    }

    public void setIndexReferenced(HashMap<Integer, Integer> indexReferenced) {
        this.indexReferenced = indexReferenced;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }
}
