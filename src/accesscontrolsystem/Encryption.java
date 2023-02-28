/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesscontrolsystem;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author Avadh
 */
public class Encryption {
    public static final String AES = "AES";
    
    // AES Algorithm with CBC mode (Block cipher)
    private static final String AESCipherAlg = "AES/CBC/PKCS5PADDING";
    
    // Function that creates the secret key to encrypt and decrypt
    public SecretKey createKey() throws NoSuchAlgorithmException {

        //SecureRandom random = new SecureRandom();
        KeyGenerator generator = KeyGenerator.getInstance(AES);
        
        generator.init(256);
        SecretKey key = generator.generateKey();
        
        return key;
    }
    
    // Function that makes the initialisation vector that avoids repetition 
    // during encryption, essentially a random number
    public byte[] createVector() {
        
        byte[] vector = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(vector);
        
        return vector;
    }
    
    // Function that takes the plaintext, the key, and the initialisation
    // vector to convert plaintext to ciphertext
    public byte[] executeEncryption(String text, SecretKey key, byte[] vector) 
            throws NoSuchAlgorithmException, NoSuchPaddingException, 
            InvalidKeyException, InvalidAlgorithmParameterException, 
            IllegalBlockSizeException, BadPaddingException {
        
        Cipher cipher = Cipher.getInstance(AESCipherAlg);
        
        IvParameterSpec paramSpec = new IvParameterSpec(vector);
        
        cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        
        return cipher.doFinal(text.getBytes());
    }
    
    // Function that takes the ciphertext, the key, and the initialisation 
    // vector and converts it back to plaintext
    public String executeDecryption(byte[] ciphertext, SecretKey key, 
            byte[] vector) throws NoSuchAlgorithmException, NoSuchPaddingException, 
            InvalidKeyException, InvalidAlgorithmParameterException, 
            IllegalBlockSizeException, BadPaddingException {
        
        Cipher cipher = Cipher.getInstance(AESCipherAlg);
        
        IvParameterSpec paramSpec = new IvParameterSpec(vector);
        
        cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        
        byte[] result = cipher.doFinal(ciphertext);
        
        return new String(result);
    }
}
