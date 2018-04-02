/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;
import FileSystem.File;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;
import javax.crypto.spec.*;
/**
 *
 * @author costa
 */
public class AES256 {
    
    
    public static SecretKey NewKey(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
        return secret;
    }
    public static byte[] EncryptFile(SecretKey secret, File file) 
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, 
            UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
        file.setIV(params.getParameterSpec(IvParameterSpec.class).getIV());
        return file.setData(cipher.doFinal(file.getData().getBytes("UTF-8")));
    }
    public static byte[] DecryptFile(SecretKey secret, File file) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        System.out.println((secret==null) + " " + file.getIV());
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(file.getIV()));
        String plaintext = new String(cipher.doFinal(file.getData().getBytes()), "UTF-8");
        return file.setData(plaintext.getBytes());
    }
    
    
    
}
