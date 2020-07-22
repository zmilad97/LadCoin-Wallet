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
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class WalletService {
    private static final Logger LOG = LoggerFactory.getLogger(WalletService.class);
    @Value("${app.core-address}")
    private String coreAddress;
    private ECDSA ecdsa = new ECDSA();
    private Wallet wallet = new Wallet();
    private StarterService starterService;
    private HashMap<String, Object> walletStatus;  //  ==  privateKey , publicKey , balance , Transactions snapshots

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


    public Map<String, Transaction> newTransaction (HashMap<String, String> transactionDetails) {
        Transaction transaction = new Transaction();
        Transaction UTXOs = starterService.getUTXOs();
        TransactionInput transactioninput = new TransactionInput();
        TransactionOutput transactionOutput = new TransactionOutput();
        Map<String, Transaction> transactionMap = new HashMap<>();

        transaction.setTransactionId(new Random(1024).toString()); // TODO : Fix Transaction Id to be Auto Generated

        transactioninput.setPreviousTransactionHash(UTXOs.getTransactionHash());
        transactioninput.setIndexReferenced(UTXOs.getTransactionInput().getIndexReferenced() + 1);
        transactioninput.setScriptSignature(starterService.getWallet().getSignature());

        transactionOutput.setAmount(Double.parseDouble(transactionDetails.get("amount")));
        transactionOutput.setPublicKeyScript(transactionDetails.get("destination"));
            transactionMap.put("Transaction" , transaction);
        if (Double.parseDouble(transactionDetails.get("amount")) <= UTXOs.getTransactionOutput().getAmount()) {
            Transaction nextUTXO = new Transaction();
            TransactionInput nextInput = new TransactionInput();
            TransactionOutput nextOutput = new TransactionOutput();

            nextInput.setPreviousTransactionHash(UTXOs.getTransactionHash());
            nextInput.setScriptSignature(starterService.getWallet().getSignature());
            nextInput.setIndexReferenced(UTXOs.getTransactionInput().getIndexReferenced() + 2);

            nextOutput.setPublicKeyScript(Base64.getEncoder().encodeToString(starterService.getWallet().getPublicKey().getEncoded()));
            nextOutput.setAmount(UTXOs.getTransactionOutput().getAmount() - Double.parseDouble(transactionDetails.get("amount")));

            transactionMap.put("UTXOs" , nextUTXO);
        }

        return transactionMap;
    }

    public String sendTransaction(Transaction transaction) {
        Gson gson = new Gson();
        StringEntity params;
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
        return "unknown error";
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
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    public Wallet getWallet() {
        return wallet;
    }
}




