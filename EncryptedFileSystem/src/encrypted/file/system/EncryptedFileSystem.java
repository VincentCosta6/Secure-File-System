/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encrypted.file.system;

import FileSystem.Data;
import FileSystem.Root;
import Security.AES256;
import Security.SHA256;
import javax.crypto.SecretKey;

/**
 *
 * @author costa
 */
public class EncryptedFileSystem {

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
    public static boolean setData(Data _data)
    {
        data = _data;
        return true;
    }
    /**
     * Encrypts the password given and checks it against the encrypted password, immune to time attacks
     * @param guess the password to check
     * @return password is correct
     */
    public static boolean checkPassword(String guess)
    {
        System.out.println(data);
        return SHA256.PasswordCheck(guess, new String(data.getCheck()));
    }
    static
    {
        java.security.Security.setProperty("crypto.policy", "unlimited");
    }
}
