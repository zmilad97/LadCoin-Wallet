package com.github.zmilad97.restfulblockchainwallet.Service;

import com.github.zmilad97.restfulblockchainwallet.Config.Config;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class WalletService {

    Cryptography cryptography = new Cryptography();
    Random random = new Random();

    public List<String> generateWallet(Config config) throws NoSuchAlgorithmException {
        String priKey = cryptography.toHexString(cryptography.getSha(String.valueOf(random.nextDouble())));
        String pubKey = cryptography.toHexString(cryptography.getSha(String.valueOf(random.nextDouble())));
        List<String> keys = new ArrayList<>();
        System.out.println("priKey" + priKey);
        System.out.println("pubKey" + pubKey);

        try {

            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(config.getAddress()+"/add");
            StringEntity params = new StringEntity(priKey);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(params);
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println(httpResponse.getEntity().getContent());
            keys.add(priKey);
            keys.add(pubKey);
            return keys;
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        return null;
    }

}
