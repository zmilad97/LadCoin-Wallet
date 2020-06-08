package com.github.zmilad97.restfulblockchainwallet.Service;

import com.github.zmilad97.restfulblockchainwallet.Module.Transaction.Transaction;
import com.github.zmilad97.restfulblockchainwallet.Security.ECDSA;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
public class WalletService {
    private static final Logger LOG = LoggerFactory.getLogger(WalletService.class);

    @Value("${app.core-address}")
    private String coreAddress;
    private ECDSA ecdsa = new ECDSA();

    public void generateWallet() {
        ecdsa.generateKeys();
        System.out.println("Private key : " + ecdsa.getPrivateKey());
        System.out.println("Public Key : " + ecdsa.getPublicKey());
        System.out.println("PublicKey in string " + Base64.getEncoder().encodeToString(ecdsa.getPublicKey().getEncoded()));
        String pubKeyHash =Base64.getEncoder().encodeToString(ecdsa.getPublicKey().getEncoded());
        System.out.println(ecdsa.signData(ecdsa.getPrivateKey(),pubKeyHash));

    }

    public String newTransaction(Transaction transaction) {
        Gson gson = new Gson();
        StringEntity params;
        String error = "unknown error";
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(this.coreAddress + "/transaction/new");
            params = new StringEntity(gson.toJson(transaction));
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println(httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return "transaction added";
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return error;
    }

    //TODO : COMPLETE THIS METHOD
    public String getCoreAddress() {
        return coreAddress;
    }

    public String connectionTest() {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(this.coreAddress + "/connectionTest");
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            return String.valueOf(httpResponse.getStatusLine().getStatusCode());
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
}




