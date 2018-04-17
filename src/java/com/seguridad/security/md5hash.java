package com.seguridad.security;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

/**
 *
 * @author Usuario
 */
public class md5hash {

    public static void main(String[] args) {
        //add the security provider
        //not required if you have Install the library
        //by Configuring the Java Runtime
        Security.addProvider(new BouncyCastleProvider());

        //this is the input;
        byte input[] = {0x00};
        String entry = "Hola Mundo";
        try {
            input = entry.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(md5hash.class.getName()).log(Level.SEVERE, null, ex);
        }
        //update the input of MD5
        SHA1Digest md5 = new SHA1Digest();
        md5.update(input, 0, input.length);

        //get the output/ digest size and hash it
        byte[] digest = new byte[md5.getDigestSize()];
        md5.doFinal(digest, 0);

        //show the input and output
        System.out.println("Entry message: " + entry);
        System.out.println("Input (hex): "
                + new String(Hex.encode(input)));
        System.out.println("Output (hex): "
                + new String(Hex.encode(digest)));
    }

    public static String sha1(String texto) {
        String textoCifrado;
        //add the security provider
        //not required if you have Install the library
        //by Configuring the Java Runtime
        Security.addProvider(new BouncyCastleProvider());

        //this is the input;
        byte input[] = {0x00};
        String entry = texto;
        try {
            input = entry.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(md5hash.class.getName()).log(Level.SEVERE, null, ex);
        }
        //update the input of MD5
        SHA1Digest md5 = new SHA1Digest();
        md5.update(input, 0, input.length);

        //get the output/ digest size and hash it
        byte[] digest = new byte[md5.getDigestSize()];
        md5.doFinal(digest, 0);

        //show the input and output
        System.out.println("Entry message: " + entry);

        System.out.println("Input (hex): "
                + new String(Hex.encode(input)));
        textoCifrado = new String(Hex.encode(digest));
        System.out.println("Output (hex): "
                + textoCifrado);

        return textoCifrado;

    }

}
