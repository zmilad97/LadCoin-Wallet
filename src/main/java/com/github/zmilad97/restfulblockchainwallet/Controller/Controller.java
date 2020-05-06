package com.github.zmilad97.restfulblockchainwallet.Controller;

import com.github.zmilad97.restfulblockchainwallet.Config.Config;
import com.github.zmilad97.restfulblockchainwallet.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.List;

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

    @RequestMapping("/add")
    public List addWallet() throws NoSuchAlgorithmException {
        return walletService.generateWallet(config);
    }

    @RequestMapping(value = "/trx", method = RequestMethod.POST)
    public void newTrx() {

    }

    @RequestMapping(value = "/test")
    public void test() {
    }


}
