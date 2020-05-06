package com.github.zmilad97.restfulblockchainwallet.Config;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Scanner;

@Service
public class Config {
    private String address;

    public Config() {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File("src/main/resources/config.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                sb.append(sc.next());
            this.address = sb.toString();
            System.out.println(this.address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String setAddress(String address) {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/config.txt");

            fileWriter.flush();
            fileWriter.write(address);
            System.out.println("status : " + address);
            fileWriter.close();
            return "Config has been set ! new address is = " + address;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Config didnt change !";
    }
    public String getAddress(){
        return this.address;
    }
    public String connectionTest() {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(this.address + "/connectionTest");
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            return String.valueOf(httpResponse.getStatusLine().getStatusCode());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
