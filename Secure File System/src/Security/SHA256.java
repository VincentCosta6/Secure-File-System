/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author costa
 */
public class SHA256 {
    public static String Encrypt(String unsecuredMessage)
    {
        try 
        {
            return Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest(unsecuredMessage.getBytes(StandardCharsets.UTF_8)));
        } 
        catch (NoSuchAlgorithmException ex) 
        {
            Logger.getLogger(SHA256.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
            return "No Algorithm";
        }
    }
    public static boolean PasswordCheck(String preSHA, String compareTo)
    {
        return NoTimeAttack(Encrypt(preSHA),(compareTo));
    }
    public static boolean NoTimeAttack(String a1, String b1)
    {
        byte[] a = Encrypt(a1).getBytes();
        byte[] b = Encrypt(b1).getBytes();
        int result = 0;
        for (int i = 0; i < a.length; i++) {
          result |= a[i] ^ b[i];
        }
        return result == 0;
        }
        
}
