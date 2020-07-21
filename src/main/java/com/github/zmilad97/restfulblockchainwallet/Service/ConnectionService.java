package com.github.zmilad97.restfulblockchainwallet.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zmilad97.restfulblockchainwallet.Module.Transaction.Transaction;
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

    @Autowired
    public ConnectionService() {
    }

    public Transaction UTXOsRequest(String signature) {
        final HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        String address = coreAddress +"/UTXOs";

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(signature))
                .uri(URI.create(address))
                .setHeader("Wallet", "UTXOs")
                .build();

        HttpResponse<String> response = null;
        try {

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Transaction transaction = new ObjectMapper().readValue(response.body(),Transaction.class);
            return transaction;
        } catch (IOException | InterruptedException e) {
            LOG.error(e.getMessage());
        }
         return null;
    }




}
