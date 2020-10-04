
package com.github.zmilad97.restfulblockchainwallet.Controller;

import com.github.zmilad97.restfulblockchainwallet.Module.Wallet;
import com.github.zmilad97.restfulblockchainwallet.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WalletController {
    //TODO:Connection test,new trx,new wallet , current wallet status
    private final WalletService walletService;
    private final Scanner sc = new Scanner(System.in);


    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    public void controller() {
        System.out.println("  .::  Main Menu  ::.   \n");
        listOptions();
        try {
            switchCase(sc.nextInt());
        } catch (InputMismatchException | IllegalStateException e) {
            switchCase(0);
        }
    }

    private void switchCase(int selectedOption) {
        switch (selectedOption) {
            default:
                System.out.println("Entered option is not valid ! pls select a listed option");
                controller();
                break;
            case 1:
                if (walletService.connectionTest().equals("200"))
                    System.out.println("Wallet is connected to the BlockChain core");
                else
                    System.out.println("Connection problem , wallet is not connected to the BlockChain core !");
                break;

            case 2:
                System.out.println(walletService.getCoreAddress());
                break;

            case 3:
                HashMap<String, String> transactionDetailsMap = transactionDetails();
                if (transactionDetailsMap != null)
                    walletService.sendTransaction(walletService.newTransaction(transactionDetailsMap));
                break;

            case 4:
                walletService.generateWallet();
                break;

            case 5:
                currentWalletStatus();
                break;
            case 6:
                System.exit(0);
        }
        System.out.println("");
        controller();
    }

    private void currentWalletStatus(){
        Wallet currentWallet = walletService.getWallet();
        System.out.println("Wallet PrivateKey : "+ Base64.getEncoder().encodeToString(currentWallet.getPrivateKey().getEncoded()));
        System.out.println("Wallet PublicKey : "+ Base64.getEncoder().encodeToString(currentWallet.getPublicKey().getEncoded()));
        System.out.println("Wallet Signature : "+currentWallet.getSignature());
        System.out.println("Wallet Balance : " +currentWallet.getBalance());
        System.out.println("Do you want to see unspent transaction outputs (UTXOs)?  y/n");
        if(sc.nextLine().equals("y")||sc.nextLine().equals("Y"))
            System.out.println(currentWallet.getUTXOs());
        else
            controller();
    }

    private HashMap<String, String> transactionDetails() {
        HashMap<String, String> transactionDetailsMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        try {

            transactionDetails().put("publicKey", Base64.getEncoder()
                    .encodeToString(walletService.getWallet().getPublicKey().getEncoded()));
        }catch (NullPointerException e){
            System.out.println("Wallet is not loaded , maybe you have no wallet , try to create one");
            controller();
        }

        System.out.println("~~~~ Enter destination signature :");
        transactionDetailsMap.put("signature", scanner.nextLine());

        System.out.println("~~~~ Enter amount :");
        transactionDetailsMap.put("amount", scanner.nextLine());

        System.out.println("Transaction Details : ");
        System.out.println("TransactionInput publicKey : " + transactionDetailsMap.get("publicKey"));
        System.out.println("TransactionOutPut signature : " + transactionDetailsMap.get("signature"));
        System.out.println("Transaction amount : " + transactionDetailsMap.get("amount"));

        System.out.println("Confirm transaction ? (y/n)");
        if (scanner.nextLine().equals("y"))
            return transactionDetailsMap;
        else
            return null;
    }

    private void listOptions() {
        System.out.println("1 - Connection Test");
        System.out.println("2 - Current Node");
        System.out.println("3 - New Transaction");
        System.out.println("4 - New Wallet");
        System.out.println("5 - Current Wallet Status");
        System.out.println("6 - Exit");

    }
}

