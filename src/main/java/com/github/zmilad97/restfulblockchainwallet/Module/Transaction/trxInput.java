package com.github.zmilad97.restfulblockchainwallet.Module.Transaction;

public class trxInput {
 private String previousTransactionHash;
 private String indexReferenced;
 private String scriptSignature;  //proves that i own the pubKeyScript in trxOutput contains Signature(privateKey,pubKey)

 public String getPreviousTransactionHash() {
  return previousTransactionHash;
 }

 public void setPreviousTransactionHash(String previousTransactionHash) {
  this.previousTransactionHash = previousTransactionHash;
 }

 public String getIndexReferenced() {
  return indexReferenced;
 }

 public void setIndexReferenced(String indexReferenced) {
  this.indexReferenced = indexReferenced;
 }

 public String getScriptSignature() {
  return scriptSignature;
 }

 public void setScriptSignature(String scriptSignature) {
  this.scriptSignature = scriptSignature;
 }
}
