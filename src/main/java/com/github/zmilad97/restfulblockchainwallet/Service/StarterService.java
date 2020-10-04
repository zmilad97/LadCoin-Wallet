package com.github.zmilad97.restfulblockchainwallet.Service;

import com.github.zmilad97.restfulblockchainwallet.Module.Transaction.Transaction;
import com.github.zmilad97.restfulblockchainwallet.Module.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;
import java.util.List;

@Service
public class StarterService {
    private static final Logger LOG = LoggerFactory.getLogger(StarterService.class);
    private ConnectionService connectionService;

    private Wallet wallet;
    private double currentBalance = 0;
    private List<Transaction> UTXOs;

    @Autowired
    public StarterService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public void loadWallet() {
        try {
            FileInputStream fileInputStream = new FileInputStream("Wallet.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            wallet = (Wallet) objectInputStream.readObject();
            if (wallet != null) {
                LOG.info("signature : " + wallet.getSignature());
                LOG.info("publicKey : " + Base64.getEncoder().encodeToString(wallet.getPublicKey().getEncoded()));
                String publicKey = Base64.getEncoder().encodeToString(wallet.getPublicKey().getEncoded());
                currentBalance = getCurrentBalance(publicKey);
                wallet.setBalance(currentBalance);
                LOG.info("Wallet Loaded , Current Balance is : " + currentBalance);
            } else
                LOG.info("Wallet Not Found");
        } catch (IOException | ClassNotFoundException e) {
            LOG.error(e.getMessage());
        }
    }


    public List<Transaction> findUTXOs(String signature) {
        UTXOs = connectionService.UTXOsRequest(signature);
        return UTXOs;
    }

    public double getCurrentBalance(String signature){
        List<Transaction> UTXOsList ;
        UTXOsList = findUTXOs(signature);
        for (int i = UTXOsList.size() -1 ; i>=0 ; i--)
           this.currentBalance+= UTXOsList.get(i).getTransactionOutput().getAmount();
        return currentBalance;
    }

    public List<Transaction> getUTXOs() {
        return UTXOs;
    }

    public Wallet getWallet() {
        return wallet;
    }

}
