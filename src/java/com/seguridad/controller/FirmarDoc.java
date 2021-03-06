
package com.seguridad.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 *
 * @author josueoviedo
 */
public class FirmarDoc {
    private PrivateKey privatekey;
    private byte[] documento;
    
    FirmarDoc(PrivateKey privatekey, byte[] documento) {
       this.privatekey=privatekey;
       this.documento=documento;
    }

    public PrivateKey getPrivatekey() {
        return privatekey;
    }

    public byte[] getDocumento() {
        return documento;
    }
    
    public byte[] obtenerFirma() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        Signature firmaPrivada=Signature.getInstance("SHA256WithRSA");
	firmaPrivada.initSign(getPrivatekey());
        firmaPrivada.update(getDocumento());
        return firmaPrivada.sign();
    }

    public Boolean verificarFirma(PublicKey publickey, byte[] datosDocumento, byte[] firma) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException {
        Signature firmaPublica=Signature.getInstance("SHA256WithRSA");
        firmaPublica.initVerify(publickey);
        firmaPublica.update(datosDocumento);
        
        //VERIFICO FIRMA CON CLAVE PUBLICA
        if (firmaPublica.verify(firma) == true) {
            return true;
        }
        else {
            return false;
        }
    }
}