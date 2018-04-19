/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controller;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author josueoviedo
 */
public final class ParClaves {
    private KeyPairGenerator parkey;
    KeyPair kp;
    
    ParClaves() {

    }
    
    ParClaves(KeyPairGenerator kg) {
        parkey=kg;
        getParkey().initialize(2048);
        kp=getParkey().generateKeyPair();
    }
    
            
    public KeyPairGenerator getParkey() {
        return parkey;
    }
    
    //OBTENGO CLAVE PUBLICA Y PRIVADA A PARTIR DE KEYPAIRGENERATOR Y KEYPAIR / CONVIERTO LAS CLAVES EN BYTES PARA GUARDAR EN FICHERO
    public byte[] clavePublica(){  
        PublicKey kpu=kp.getPublic();
        return  kpu.getEncoded();   
    }
    
    public byte[] clavePrivada(){
        PrivateKey kpr=kp.getPrivate();
        return kpr.getEncoded();
    }
    
    public static PublicKey invertirPublica(byte[] fichero) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec clavepublica=new X509EncodedKeySpec(fichero);
        KeyFactory keyFactoryPublico1 = KeyFactory.getInstance("RSA");
        return keyFactoryPublico1.generatePublic(clavepublica);
    }
    
    public static PrivateKey invertirPrivada(byte[] fichero) throws InvalidKeyException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        PKCS8EncodedKeySpec claveprivada=new PKCS8EncodedKeySpec(fichero);
	KeyFactory keyFactoryPrivada = KeyFactory.getInstance("RSA");
	return keyFactoryPrivada.generatePrivate(claveprivada);     
    } 
     
}