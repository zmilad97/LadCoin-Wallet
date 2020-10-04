package com.github.zmilad97.restfulblockchainwallet.Service;

import com.github.zmilad97.restfulblockchainwallet.Module.Transaction.Transaction;
import com.github.zmilad97.restfulblockchainwallet.Module.Transaction.TransactionInput;
import com.github.zmilad97.restfulblockchainwallet.Module.Transaction.TransactionOutput;
import com.github.zmilad97.restfulblockchainwallet.Module.Wallet;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

@Service
public class WalletService {
    private static final Logger LOG = LoggerFactory.getLogger(WalletService.class);
    @Value("${app.core-address}")
    private String coreAddress;
    private ECDSA ecdsa = new ECDSA();
    private Wallet wallet = new Wallet();
    private StarterService starterService;

    @Autowired
    public WalletService(StarterService starterService) {
        this.starterService = starterService;
    }

    public void generateWallet() {
        ecdsa.generateKeys();
        System.out.println("Private key : " + ecdsa.getPrivateKey());
        System.out.println("Public Key : " + ecdsa.getPublicKey());
        System.out.println("PublicKey in string " + Base64.getEncoder().encodeToString(ecdsa.getPublicKey().getEncoded()));
        String pubKeyHash = Base64.getEncoder().encodeToString(ecdsa.getPublicKey().getEncoded());
        System.out.println("Signature :" + ecdsa.signData(ecdsa.getPrivateKey(), pubKeyHash));

        wallet.setPrivateKey(ecdsa.getPrivateKey());
        wallet.setPublicKey(ecdsa.getPublicKey());
        wallet.setSignature(ecdsa.signData(ecdsa.getPrivateKey(), pubKeyHash));
        wallet.setLastTransactionHash(null);
        wallet.setBalance(0);
        wallet.setUTXOs(null);

        saveWallet(wallet);

    }


    public void saveWallet(Wallet wallet) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("Wallet.dat");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(wallet);
            LOG.info("Wallet Saved On This System ! ");
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    public Map<String, Transaction> newTransaction(HashMap<String, String> transactionDetails) {
        Transaction transaction = new Transaction();
        List<Transaction> UTXOsList = starterService.getUTXOs();
        TransactionInput transactioninput = new TransactionInput();
        TransactionOutput transactionOutput = new TransactionOutput();
        Map<String, Transaction> transactionMap = new HashMap<>();

       transaction.setTransactionId("404");


        //Choosing UTXOs for transactionInput
        List<Transaction> chosenUTXOs = chooseUTXOs(UTXOsList, Double.parseDouble(transactionDetails.get("amount")));
        if (chosenUTXOs.size() == 0){
            transactionMap.put("Null",null);
            return transactionMap;
        }

        //adding chosen Transaction to map and calculating the total of transactions amount
        Map<Integer, Transaction> UMap = new HashMap<>();
        double total = 0;
        for (int i = 0; i < chosenUTXOs.size(); i++) {
            UMap.put(i, chosenUTXOs.get(i));
            total += chosenUTXOs.get(i).getTransactionOutput().getAmount();
            transactioninput.addPreviousTransactionHash(i, chosenUTXOs.get(i).getTransactionHash());
        }
        if (total != Double.parseDouble(transactionDetails.get("amount"))) {
            Transaction nextUTXO = new Transaction();
            TransactionInput nextInput = new TransactionInput();
            TransactionOutput nextOutput = new TransactionOutput();

            nextInput.addPreviousTransactionHash(0, chosenUTXOs.get(chosenUTXOs.size() - 1).getTransactionHash());
            nextInput.setPubKey(Base64.getEncoder().encodeToString(starterService.getWallet().getPublicKey().getEncoded()));

            nextOutput.setSignature(starterService.getWallet().getSignature());
            nextOutput.setAmount(total - Double.parseDouble(transactionDetails.get("amount")));

            transactionMap.put("UTXOs", nextUTXO);
        }
        for (int i = 0; i < chosenUTXOs.size() - 1; i++)
            transactioninput.addPreviousTransactionHash(i, chosenUTXOs.get(i).getTransactionHash());
        transactioninput.setPubKey(Base64.getEncoder().encodeToString(starterService.getWallet().getPublicKey().getEncoded()));

        transactionOutput.setAmount(Double.parseDouble(transactionDetails.get("amount")));
        transactionOutput.setSignature(transactionDetails.get("destination"));
        transactionMap.put("Transaction", transaction);


        return transactionMap;
    }

    public List<Transaction> chooseUTXOs(List<Transaction> UTXOsList, double amount) {
        double total = 0;
        List<Transaction> UTXOs = new ArrayList<>();
//        for (int i = UTXOsList.size() - 1; i >= 0; i--)
//            if (UTXOsList.get(i).getTransactionOutput().getAmount() >= amount){
//                UTXOs.add(UTXOsList.get(i));
//                return UTXOs;
//            }
        for (int i = UTXOsList.size() - 1; i >= 0; i--) {
            total += UTXOsList.get(i).getTransactionOutput().getAmount();
            UTXOs.add(UTXOsList.get(i));
            if (total >= amount)
                return UTXOs;
        }
        return new ArrayList();
    }

    public String sendTransaction(Map<String, Transaction> transactionMap) {
        if(transactionMap.containsKey("Null"))
            return "UTXOs is null";
        Gson gson = new Gson();
        StringEntity params;
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(this.coreAddress + "/transaction/new");
            params = new StringEntity(gson.toJson(transactionMap));
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
        return "unknown error";
    }

    public List<Transaction> showUTXOs() {
        return starterService.getUTXOs();
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }


    public String getCoreAddress() {
        return coreAddress;
    }

    public String connectionTest() {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(this.coreAddress + "/connectionTest");
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            return String.valueOf(httpResponse.getStatusLine().getStatusCode());
        } catch (IOException ignored) {
        }
        return "404";
    }

    public Wallet getWallet() {
        return starterService.getWallet();
    }
}




