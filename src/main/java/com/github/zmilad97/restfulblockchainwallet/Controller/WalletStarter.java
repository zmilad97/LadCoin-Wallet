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

    @Autowired
    public WalletStarter(StarterService starterService) {
        this.starterService = starterService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("Checking wallet status , Please wait ! ...");
        starterService.loadWallet();
//      starterService.UTXOs();
    }

}
