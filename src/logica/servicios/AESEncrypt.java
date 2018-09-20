/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.servicios;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Clase que implementa la encriptación o desencriptación de contraseñas.
 * @author Mouse
 */
public class AESEncrypt {

    private SecretKey key;
    private Cipher cipher;
    private String algoritmo = "AES";
    private int keysize = 16;

    /**
     * Crea la Llave para encriptar/desencriptar
     *
     * @param value clave para encriptar
     */
    public void addKey(String value) {
        byte[] valuebytes = value.getBytes();
        key = new SecretKeySpec(Arrays.copyOf(valuebytes, keysize), algoritmo);
    }

    /**
     * Metodo para encriptar un texto
     *
     * @param texto texto a encriptar
     * @return String texto encriptado
     */
    public String encriptar(String texto) {
        String value = "";
        try {
            cipher = Cipher.getInstance(algoritmo);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] textobytes = texto.getBytes();
            byte[] cipherbytes = cipher.doFinal(textobytes);
            value = new BASE64Encoder().encode(cipherbytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | 
                    InvalidKeyException | IllegalBlockSizeException | 
                    BadPaddingException ex) {
            System.err.println(ex.getMessage());
        }
        return value;
    }

    /**
     * Metodo para desencriptar un texto
     *
     * @param texto Texto encriptado
     * @return String texto desencriptado
     */
    public String desencriptar(String texto) {
        String str = "";
        try {
            byte[] value = new BASE64Decoder().decodeBuffer(texto);
            cipher = Cipher.getInstance(algoritmo);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] cipherbytes = cipher.doFinal(value);
            str = new String(cipherbytes);
        } catch (InvalidKeyException | IllegalBlockSizeException | 
                    BadPaddingException | IOException | 
                    NoSuchAlgorithmException | NoSuchPaddingException ex) {
            System.err.println(ex.getMessage());
        }
        return str;
    }
}
