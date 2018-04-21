package com.seguridad.security;

import java.util.Arrays;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

public class AESECB {

    public static byte[] encrypt(byte[] key, byte[] input) throws Exception {
        return processing(key, input, true);
    }

    public static byte[] decrypt(byte[] key, byte[] input) throws Exception {
        byte res1[] = processing(key, input, false);
        int i = res1.length - 1;
        while (res1[i] == 0x00) {
            i--;
        }
        byte res0[] = new byte[i + 1];
        System.arraycopy(res1, 0, res0, 0, res0.length);
        return res0;
    }

    private static byte[] processing(byte[] key, byte[] input, boolean encrypt) throws Exception {
        PaddedBufferedBlockCipher pbbc = new PaddedBufferedBlockCipher(new AESEngine(), new PKCS7Padding());
        pbbc.init(encrypt, new KeyParameter(key));
        byte[] output = new byte[pbbc.getOutputSize(input.length)];
        int bytesWrittenOut = pbbc.processBytes(input, 0, input.length, output, 0);
        pbbc.doFinal(output, bytesWrittenOut);
        return output;
    }

    private static final byte[] KEY_128 = {
        0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
        0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F,};

    private static final byte[] KEY_192 = {
        0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
        0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F,
        0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,};

    private static final byte[] KEY_256 = {
        0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
        0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F,
        0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
        0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F,};

    public static void main(String[] args) throws Exception {
        
        String letters = "1234567898765432";
        //System.out.println(letters);

        // Use default charset.
        byte[] valuesDefault = letters.getBytes();
        // ... Use Arrays.toString to display our byte array.
        //System.out.println(Arrays.toString(valuesDefault));

        // Specify US-ASCII char set directly.
        //byte[] valuesAscii = letters.getBytes("US-ASCII");
        //System.out.println(Arrays.toString(valuesAscii));
        
        encriptar("1234567898765432", valuesDefault );
        testAESECB192();
        testAESECB256();
    }

    public static void testAESECB256() throws Exception {
        //System.out.println("--------------AES ECB Key 256 ------------------");
        byte[] normal = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F};
        //System.out.println(byteToHex(normal));
        byte[] enc = AESECB.encrypt(KEY_256, normal);
        //System.out.println(byteToHex(enc));
        byte[] dec = AESECB.decrypt(KEY_256, enc);
        //System.out.println(byteToHex(dec));
    }

    public static void testAESECB192() throws Exception {
        //System.out.println("--------------AES ECB Key 192 ------------------");
        byte[] normal = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F};
        //System.out.println(byteToHex(normal));
        byte[] enc = AESECB.encrypt(KEY_192, normal);
        //System.out.println(byteToHex(enc));
        byte[] dec = AESECB.decrypt(KEY_192, enc);
        //System.out.println(byteToHex(dec));
    }

    public static byte[] encriptar(String key, byte[] file) throws Exception {
        //System.out.println("--------------AES ECB Key 128 ------------------");
        
        String letters = key;
        //System.out.println(key);

        // Use default charset.
        byte[] valuesDefault = letters.getBytes();
        // ... Use Arrays.toString to display our byte array.
        //System.out.println(Arrays.toString(valuesDefault));

        // Specify US-ASCII char set directly.
        //byte[] valuesAscii = letters.getBytes("US-ASCII");
        //System.out.println(Arrays.toString(valuesAscii));
        
        //byte[] normal = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F};
        //System.out.println(byteToHex(valuesDefault));
        byte[] enc = AESECB.encrypt(valuesDefault, file);
        //System.out.println(byteToHex(enc));
        //System.out.println(byteToHex(dec));
       
        return enc;
    }
    
    public static byte[] descriptar(String key, byte[] file) throws Exception{
        
        //System.out.println("--------------AES ECB Key 128 ------------------");
        
        String letters = key;
        //System.out.println(key);

        // Use default charset.
        byte[] valuesDefault = letters.getBytes();
        // ... Use Arrays.toString to display our byte array.
        System.out.println(Arrays.toString(valuesDefault));

        // Specify US-ASCII char set directly.
        //byte[] valuesAscii = letters.getBytes("US-ASCII");
        //System.out.println(Arrays.toString(valuesAscii));
        
        //byte[] normal = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F};
        byte[] dec = AESECB.decrypt(valuesDefault, file);
        //System.out.println(byteToHex(dec));
        
        return dec;    
    }
            

    public static String byteToHex(byte[] data) {
        StringBuilder localStringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String str;
            if ((str = Integer.toHexString(data[i] & 0xFF).toUpperCase()).length() == 1) {
                localStringBuilder.append(0);
            }
            localStringBuilder.append(str).append(" ");
        }
        return localStringBuilder.substring(0, localStringBuilder.length() - 1);
    }

}
