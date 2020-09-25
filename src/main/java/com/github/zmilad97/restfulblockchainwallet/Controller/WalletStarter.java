package com.github.zmilad97.restfulblockchainwallet.Controller;

import com.github.zmilad97.restfulblockchainwallet.Service.StarterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class WalletStarter implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(WalletStarter.class);
    private final StarterService starterService;
    private final WalletController walletController ;

    @Autowired
    public WalletStarter(StarterService starterService, WalletController walletController) {
        this.starterService = starterService;
        this.walletController = walletController;
    }

    @Override
    public void run(ApplicationArguments args)  {
        LOG.info("Checking wallet status , Please wait ! ...");
        starterService.loadWallet();
        walletController.controller();
//      starterService.UTXOs();
    }

}
