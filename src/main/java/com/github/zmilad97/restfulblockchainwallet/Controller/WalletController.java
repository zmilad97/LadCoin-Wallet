package com.github.zmilad97.restfulblockchainwallet.Controller;

import com.github.zmilad97.restfulblockchainwallet.Module.Transaction.TRX;
import com.github.zmilad97.restfulblockchainwallet.Service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {
    private static final Logger LOG = LoggerFactory.getLogger(WalletController.class);
    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @RequestMapping("/config/current")
    public void currentConfig() {
        LOG.info(walletService.getCoreAddress());
    }

    @RequestMapping("/connectionTest")
    public void testConnection(){
        if (walletService.connectionTest().equals("200"))
            LOG.info( "Wallet is connected to the BlockChain core");
        else
            LOG.info("Connection problem , wallet is not connected to the BlockChain core !");
    }

    @RequestMapping("/wallet/new")
    public void newWallet() {
     walletService.generateWallet();
    }

    @RequestMapping(value = "/transaction/new", method = RequestMethod.POST)
    public void newTrx(@RequestBody TRX transaction) {
               walletService.trx(transaction);           //TODO : MAKE TRANSACTION METHOD
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
