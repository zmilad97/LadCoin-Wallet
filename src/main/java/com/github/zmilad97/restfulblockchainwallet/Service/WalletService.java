package com.github.zmilad97.restfulblockchainwallet.Service;

import com.github.zmilad97.restfulblockchainwallet.Config.Config;
import com.github.zmilad97.restfulblockchainwallet.Module.Transaction;
import com.github.zmilad97.restfulblockchainwallet.Security.AsymmetricCryptography;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class WalletService {

    Cryptography cryptography = new Cryptography();
    Random random = new Random();
    AsymmetricCryptography generateKeys = new AsymmetricCryptography();
/*
    public List<String> generateWallet(Config config) throws NoSuchAlgorithmException {
        String priKey = cryptography.toHexString(cryptography.getSha(String.valueOf(random.nextDouble())));
        String pubKey = cryptography.toHexString(cryptography.getSha(String.valueOf(random.nextDouble())));
        List<String> keys = new ArrayList<>();
        System.out.println("priKey" + priKey);
        System.out.println("pubKey" + pubKey);

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(config.getAddress() + "/wallet/add");
            StringEntity params = new StringEntity(priKey);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode() == 200){
            System.out.println(httpResponse.getEntity().getContent());
            keys.add(priKey);
            keys.add(pubKey);
            return keys;}
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        return null;
    }
*/
    public List<Key> generateWallet(){
        generateKeys.generateKeys();
        System.out.println("Private key : " + generateKeys.getPrivateKey());
        System.out.println("Public Key : " + generateKeys.getPublicKey());
        List <Key> keys = new ArrayList<>();
        keys.add(generateKeys.getPrivateKey());
        keys.add(generateKeys.getPublicKey());
        return keys;
    }

    public String newTransaction(Transaction transaction,Config config) {
        Gson gson = new Gson();
        StringEntity params;
        String error = "unknown error" ;
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(config.getAddress()+"/transaction/new");
            params = new StringEntity(gson.toJson(transaction));
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println(httpResponse.getStatusLine().getStatusCode());
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                return "transaction added";
            }
        } catch (UnsupportedEncodingException e) {
            error = e.getMessage();
        } catch (ClientProtocolException e) {
            error = e.getMessage();
        } catch (IOException e) {
            error = e.getMessage();
        }
        return error ;
    }
/*   //TODO : COMPLETE THIS METHOD
    private HttpResponse httpPostCall(String address,Object object){
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost();
        StringEntity params;
        try {
            params = new StringEntity(new Gson().toJson(object));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }*/



}
