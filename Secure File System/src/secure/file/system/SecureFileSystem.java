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
    public static String password = "Wow";
    public static SecretKey myKey;
    static
    {
        java.security.Security.setProperty("crypto.policy", "unlimited");
    }
    public static void main(String[] args)
    {
        //Root object = Root.NewRoot("CollegeApplications", Root.masterRoot);
        //Root object2 = Root.NewRoot("CollegeKills", object);
        Data data = Data.Startup(Root.masterRoot.myFile);
        waitForPassword(data);
        //File file = File.newFile(Root.masterRoot, "NiceHead");
        
        Data.Construct(Root.masterRoot);
        String notDe = "I am fully exposed";
        
        File NiceHead = File.newFile(Root.masterRoot, "Wowwy");
        //NiceHead.setData(notDecrypted);
        System.out.println(myKey);
        
        NiceHead.setData(notDe);
        
        try {
            AES256.EncryptFile(myKey, NiceHead);
            
            
            //Data.newConstruct(Data.Directories.get(0));
            
//        for(File f:Data.Files)
//            System.out.println(f.name);
//        for(Root r:Data.Directories)
//            System.out.println(r.name);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SecureFileSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(SecureFileSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(SecureFileSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidParameterSpecException ex) {
            Logger.getLogger(SecureFileSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SecureFileSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(SecureFileSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(SecureFileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        NiceHead.WriteFile();
    }
    private static void waitForPassword(Data data)
    {
        Scanner reader;
        while(true)
        {
            System.out.println("Enter your password...");
            reader = new Scanner(System.in);
            String temp = reader.nextLine();
            try{if(SHA256.PasswordCheck(temp, new String(data.getCheck()))){System.out.println("Welcome");myKey = AES256.NewKey(temp.toCharArray(), data.getSalt());temp = null;break;}}
            catch(Exception e){System.out.println("Nice try");}
            System.out.println("Incorrect");
        }
    }
}
