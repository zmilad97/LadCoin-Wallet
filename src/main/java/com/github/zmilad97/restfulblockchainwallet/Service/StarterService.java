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

@Service
public class StarterService {
    private static final Logger LOG = LoggerFactory.getLogger(StarterService.class);
    private ConnectionService connectionService;
    private WalletService walletService;
    private Wallet wallet;
    private double currentBalance = 0;
    private Transaction UTXOs ;
    @Autowired
    public StarterService(ConnectionService connectionService, WalletService walletService) {
        this.connectionService = connectionService;
        this.walletService = walletService;
    }

    public void loadWallet() {
        try {
            FileInputStream fileInputStream = new FileInputStream("Wallet.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            wallet = (Wallet) objectInputStream.readObject();
            if (wallet != null) {
                walletService.setWallet(wallet);
                LOG.info(wallet.getSignature());
                String publicKey = Base64.getEncoder().encodeToString(wallet.getPublicKey().getEncoded());
                currentBalance = findUTXOs(publicKey).getTransactionOutput().getAmount();
                LOG.info("Wallet Loaded , Current Balance is : " + currentBalance );
            } else
                LOG.info("Wallet Not Found");
        } catch (IOException | ClassNotFoundException  e) {
            LOG.error(e.getMessage());
        }
    }
   /* public void loadWallet() {
        try {
            File file = new File("wallet.txt");
            Scanner sc = null;
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            List<String> walletDetails = new ArrayList<>();
            while (sc.hasNextLine())
                walletDetails.add(sc.nextLine());
            for(int i = 0 ; i < walletDetails.size();i++)
                LOG.info(walletDetails.get(i));
            wallet.setPrivateKey(walletDetails.get(0));
            wallet.setPublicKey(walletDetails.get(1));
            wallet.setSignature(walletDetails.get(2));
            wallet.setBalance(Double.valueOf(walletDetails.get(3)));
            wallet.setLastTransactionHash(walletDetails.get(4));
            walletService.setWallet(wallet);

        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage());
        }
    }*/

    //TODO : Make A Method To Get All The UTXOs

    public Transaction findUTXOs(String s) {
        return connectionService.UTXOsRequest(s);
    }

    public Wallet getWallet() {
        return wallet;
    }

}