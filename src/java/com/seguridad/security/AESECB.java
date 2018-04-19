/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.security;

import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * @version 1.0 Clase que contiene los métodos públicos encrypt y descrypt,
 * cuyos objetivos son encriptar y desencriptar respectivamente, utilizando el
 * algoritmo AES en modo Modo ECB (Electronic codebook) soportando claves de
 * 128/192/256 bits Requiere la librería Bouncy Castle
 * @see <a href="https://www.bouncycastle.org/latest_releases.html">Bouncy
 * Castle</a>
 * @see
 * <a href="http://es.wikipedia.org/wiki/Advanced_Encryption_Standard">WikiES:
 * Advanced Encryption Standard</a>
 * @see <a href="http://es.wikipedia.org/wiki/Criptograf%C3%ADa">WikiES:
 * Criptografía</a>
 * @see
 * <a href="https://es.wikipedia.org/wiki/Modos_de_operaci%C3%B3n_de_una_unidad_de_cifrado_por_bloques#Modo_ECB_.28Electronic_codebook.29">WikiES:
 * Modo ECB (Electronic codebook)</a>
 * @see <a href="http://www.linkedin.com/in/juliofcv">Julio Chinchilla</a>
 * @author Julio Chinchilla
 */
public class AESECB {

    /**
     * Función de tipo arreglo de bytes que recibe una llave (key) y un arreglo
     * de bytes (input) el cual se desea encriptar
     *
     * @param key recibe únicamente claves de 128/192/256 bits
     * @param input arreglo de bytes a cifrar
     * @return el texto cifrado en modo String
     * @throws Exception puede devolver excepciones de los siguientes tipos:
     * DataLengthException, InvalidCipherTextException
     */
    public static byte[] encrypt(byte[] key, byte[] input) throws Exception {
        return processing(key, input, true);
    }

    /**
     * Función de tipo arreglo de bytes que recibe una llave (key) y un arreglo
     * de bytes (input) el cual se desea desencriptar
     *
     * @param key recibe únicamente claves de 128/192/256 bits
     * @param input arreglo de bytes a descifrar
     * @return el texto cifrado en modo String
     * @throws Exception puede devolver excepciones de los siguientes tipos:
     * DataLengthException, InvalidCipherTextException
     */
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

    /**
     * Clase interna de procesamiento que utiliza el API de Bouncy Castle
     *
     * @param key recibe únicamente claves de 128/192/256 bits
     * @param input arreglo de bytes a codificar
     * @param encrypt true para encriptar y false para desencriptar
     * @return
     * @throws Exception
     */
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
        testAESECB128();
        testAESECB192();
        testAESECB256();
    }

    private static void testAESECB256() throws Exception {
        System.out.println("--------------AES ECB Key 256 ------------------");
        byte[] normal = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F};
        System.out.println(byteToHex(normal));
        byte[] enc = AESECB.encrypt(KEY_256, normal);
        System.out.println(byteToHex(enc));
        byte[] dec = AESECB.decrypt(KEY_256, enc);
        System.out.println(byteToHex(dec));
    }

    private static void testAESECB192() throws Exception {
        System.out.println("--------------AES ECB Key 192 ------------------");
        byte[] normal = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F};
        System.out.println(byteToHex(normal));
        byte[] enc = AESECB.encrypt(KEY_192, normal);
        System.out.println(byteToHex(enc));
        byte[] dec = AESECB.decrypt(KEY_192, enc);
        System.out.println(byteToHex(dec));
    }

    private static void testAESECB128() throws Exception {
        System.out.println("--------------AES ECB Key 128 ------------------");
        byte[] normal = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F};
        System.out.println(byteToHex(normal));
        byte[] enc = AESECB.encrypt(KEY_128, normal);
        System.out.println(byteToHex(enc));
        byte[] dec = AESECB.decrypt(KEY_128, enc);
        System.out.println(byteToHex(dec));
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
