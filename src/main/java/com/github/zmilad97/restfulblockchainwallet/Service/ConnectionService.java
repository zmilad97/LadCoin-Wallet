package com.github.zmilad97.restfulblockchainwallet.Service;

import com.github.zmilad97.restfulblockchainwallet.Module.Transaction.Transaction;
import com.github.zmilad97.restfulblockchainwallet.Security.Cryptography;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ConnectionService {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionService.class);
    @Value("${app.core-address}")
    private String coreAddress;
    private Cryptography cryptography;

    @Autowired
    public ConnectionService(Cryptography cryptography) {
        this.cryptography = cryptography;
    }

    //TODO :This Method Need TO be Tested
   /* public Transaction UTXOsRequest(String signature) {
        final CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(coreAddress + "/UTXOs");
        Gson gson = new Gson();
        StringEntity params;
        HttpResponse<String> response = null;
        try {
            params = new StringEntity(gson.toJson(signature));
            httpPost.setEntity(params);
            response = (HttpResponse<String>) httpClient.execute(httpPost);
            if (response.statusCode() == 404)
                return null;
            Transaction UTXOs = gson.fromJson(response.body(), Transaction.class);
            LOG.debug(UTXOs.getTransactionHash());

        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return null;
    }*/

    public Transaction UTXOsRequest(String signature) {

        final HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        String address = coreAddress +"/UTXOs";

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(signature))
                .uri(URI.create(address))
                .setHeader("UTXOs", "Miner")
                .build();

        HttpResponse<String> response = null;
        try {

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        if(response.statusCode() == 404)
            return null;
        //Converting response to Block Object
        Gson gson = new Gson();
        Transaction UTXOs = gson.fromJson(response.body(), Transaction.class);
        LOG.debug(UTXOs.getTransactionId());
        return UTXOs;
    }




}
