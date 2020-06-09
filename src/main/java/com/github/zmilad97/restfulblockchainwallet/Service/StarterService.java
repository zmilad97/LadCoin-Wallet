package com.github.zmilad97.restfulblockchainwallet.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class StarterService {
    private static final Logger LOG = LoggerFactory.getLogger(StarterService.class);

    public void loadWallet() {
        try {
            File file = new File("wallet.txt");
            Scanner sc = new Scanner(file);
            List<String> walletDetails = new ArrayList<>();
            while (sc.hasNextLine())
                walletDetails.add(sc.nextLine());
            for(int i = 0 ; i < walletDetails.size();i++)
                LOG.info(walletDetails.get(i));

        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage());
        }
    }

}
