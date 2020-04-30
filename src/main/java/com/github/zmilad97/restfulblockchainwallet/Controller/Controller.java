package com.github.zmilad97.restfulblockchainwallet.Controller;

import com.github.zmilad97.restfulblockchainwallet.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
public class Controller {
    WalletService walletService;

    @Autowired
    public Controller(WalletService walletService) {
        this.walletService = walletService;
    }

    @RequestMapping("/add")
    public void addWallet(){

    }

    @RequestMapping(value = "/trx",method = RequestMethod.POST)
    public void newTrx(){

    }

    @RequestMapping(value = "/test")
    public void test() throws NoSuchAlgorithmException {
        walletService.generateWallet();
    }


}
