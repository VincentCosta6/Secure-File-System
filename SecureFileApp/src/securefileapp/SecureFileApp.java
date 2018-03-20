/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securefileapp;
import FileSystem.Data;
import FileSystem.Root;
import encrypted.file.system.EncryptedFileSystem;
/**
 *
 * @author costa
 */
public class SecureFileApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Data data = EncryptedFileSystem.init();
        if(data==null)
        {
            Data.create("Wow");
            Data.Construct(Root.masterRoot);
        }
        boolean result = EncryptedFileSystem.checkPassword("Wow");
        System.out.println(result);
    }
    
}
