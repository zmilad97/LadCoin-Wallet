package com.github.zmilad97.restfulblockchainwallet.Controller;

import com.github.zmilad97.restfulblockchainwallet.Module.Transaction.Transaction;
import com.github.zmilad97.restfulblockchainwallet.Service.StarterService;
import com.github.zmilad97.restfulblockchainwallet.Service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class WalletController {
    private static final Logger LOG = LoggerFactory.getLogger(WalletController.class);
    private final WalletService walletService;
    private final StarterService starterService;

    @Autowired
    public WalletController(WalletService walletService, StarterService starterService) {
        this.walletService = walletService;
        this.starterService = starterService;
    }

    @RequestMapping("/connectionTest")
    public void testConnection() {
        if (walletService.connectionTest().equals("200"))
            LOG.info("Wallet is connected to the BlockChain core");
        else
            LOG.info("Connection problem , wallet is not connected to the BlockChain core !");
    }

    @RequestMapping("/wallet/new")
    public void newWallet() {
        walletService.generateWallet();
    }

    @RequestMapping(value = "/transaction/new", method = RequestMethod.POST)
    public void newTrx(@RequestBody HashMap<String,String> transactionDetails) {
        walletService.sendTransaction(walletService.newTransaction(transactionDetails));
    }
    @RequestMapping(value = "/test" , method = RequestMethod.GET)
    public List<Transaction> UTXOs(){
       return walletService.showUTXOs();
    }




}
