package com.github.zmilad97.restfulblockchainwallet.Controller;

import com.github.zmilad97.restfulblockchainwallet.Config.Config;
import com.github.zmilad97.restfulblockchainwallet.Module.Transaction;
import com.github.zmilad97.restfulblockchainwallet.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    WalletService walletService;
    Config config;

    @Autowired
    public Controller(WalletService walletService,Config config) {
        this.walletService = walletService;
        this.config = config;
    }

    @RequestMapping(value = "/config/new", method = RequestMethod.POST)
    public String newConfig(@RequestBody String address) {
        return config.setAddress(address);
    }

    @RequestMapping("/config/current")
    public String currentConfig() {
        return config.getAddress();
    }

    @RequestMapping("/connectionTest")
    public String testConnection(){
        if (config.connectionTest().equals("200"))
            return "Wallet is connected to the BlockChain core";
        else
            return "Connection problem , wallet is not connected to the BlockChain core !";
    }

    @RequestMapping("/wallet/new")
    public void newWallet() {
     walletService.generateWallet();
    }

    @RequestMapping(value = "/transaction/new", method = RequestMethod.POST)
    public String newTrx(@RequestBody Transaction transaction) {
        return walletService.newTransaction(transaction,config);                  //TODO : MAKE TRANSACTION METHOD
    }

    @RequestMapping(value = "/wallet/status", method = RequestMethod.POST)      //TODO : ASYMMETRIC  ENCRYPTION NEEDED (RSA ALGORITHM)
    public String walletStatus(@RequestBody String fullKey ){              //fullKey = priKey + # + pubKey---------
        String[] keys = fullKey.split("#");                         //keys[0]= priKey || keys[1] = pubKey--------
        return null;                                                       //Signature = f(priKey , message(transaction message))
    }

    @RequestMapping(value = "/test")
    public void test() {
    }


}
