package com.github.zmilad97.restfulblockchainwallet.Module;

public class Wallet {

    private String priKey;
    private String pubKey;


    public Wallet(String priKey, String pubKey) {
        this.priKey = priKey;
        this.pubKey = pubKey;
    }

    public Wallet(){

    }

    public String getPriKey() {
        return priKey;
    }

    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }
}
