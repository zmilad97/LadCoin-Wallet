package com.github.zmilad97.restfulblockchainwallet.Security;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public class ECDSA {
  /*TODO Signature = f(priKey , message(transaction message))
         verify : 1= ?v(signature , message, pubKey)
         outPut = hash(publicKey)
         google :  --ECDSA    ,   mathematical trap door
         transaction inputs = previous transactions so dont store the wallet in blockChain core
         input = v(signature) = (signature ,message = hash pubKey , pubKey)
         output = hash pubKey
    */

    private final ECGenParameterSpec parameterSpec = new ECGenParameterSpec("secp256k1");

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public void generateKeys() {
        {
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
                keyPairGenerator.initialize(parameterSpec, new SecureRandom());
                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                privateKey = keyPair.getPrivate();
                publicKey = keyPair.getPublic();


            } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }

        }
    }

    public String signData(PrivateKey privateKey,String hashPublicKey){
        try {
            Signature ecdsaSignature = Signature.getInstance("SHA256withECDSA");
            ecdsaSignature.initSign(privateKey);
            ecdsaSignature.update(hashPublicKey.getBytes(StandardCharsets.UTF_8));
            byte[] signature = ecdsaSignature.sign();
           return  Base64.getEncoder().encodeToString(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
    return null;
    }




    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }


}
