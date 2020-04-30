package com.github.zmilad97.restfulblockchainwallet.Service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Service
public class WalletService {

    Cryptography cryptography = new Cryptography();
    Random random = new Random();

    public void generateWallet() throws NoSuchAlgorithmException {

        String priKey = cryptography.toHexString(cryptography.getSha(String.valueOf(random.nextDouble())));
        String pubKey = cryptography.toHexString(cryptography.getSha(String.valueOf(random.nextDouble())));

        System.out.println("priKey" + priKey);
        System.out.println("pubKey" + pubKey);

        try {

            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost("http://localhost:8181/wallet/add");   //TODO : variable address
//            Gson gson = new Gson();
//
            StringEntity params = new StringEntity(priKey);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(params);
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println(httpResponse.getEntity().getContent());
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        }

    }

}
