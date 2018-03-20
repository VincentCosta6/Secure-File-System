/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secure.file.system;

import FileSystem.Data;
import FileSystem.File;
import FileSystem.Root;
import Security.AES256;
import Security.SHA256;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author costa
 */
public class SecureFileSystem {

    /**
     * @param args the command line arguments
     */
    private static SecretKey myKey;
    private static Data data;
    /**
     * Searches for salt file and constructs the File System
     * @return 
     */
    public static Data init()
    {
        return Data.Startup(Root.masterRoot.myFile);
    }
    /**
     * Encrypts the password given and checks it against the encrypted password, immune to time attacks
     * @param guess the password to check
     * @return password is correct
     */
    public static boolean checkPassword(String guess)
    {
        return SHA256.PasswordCheck(guess, new String(data.getCheck()));
    }
    static
    {
        java.security.Security.setProperty("crypto.policy", "unlimited");
    }
    private static void main(String[] args)
    {
        Data data = Data.Startup(Root.masterRoot.myFile);
        
        
        Data.Construct(Root.masterRoot);
        
        
    }
}
