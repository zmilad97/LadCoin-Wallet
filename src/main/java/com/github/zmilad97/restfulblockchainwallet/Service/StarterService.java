package com.github.zmilad97.restfulblockchainwallet.Service;

import com.github.zmilad97.restfulblockchainwallet.Module.Transaction.Transaction;
import com.github.zmilad97.restfulblockchainwallet.Module.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StarterService {
    private static final Logger LOG = LoggerFactory.getLogger(StarterService.class);
    private ConnectionService connectionService;
    private WalletService walletService ;
    private Wallet wallet;

    @Autowired
    public StarterService(ConnectionService connectionService, WalletService walletService){
        this.connectionService = connectionService;
        this.walletService = walletService;
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

    public Transaction UTXOs(String s){
        return connectionService.UTXOsRequest(s);

    }
    public Wallet getWallet() {
        return wallet;
    }
}
