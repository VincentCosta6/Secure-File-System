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
    static String forPC = "D:\\Github\\Secure-File-System\\Locker\\MasterRoot";
    static String forLaptop = "C:\\Users\\costa\\OneDrive\\Documents\\GitHub\\Secure-File-System\\Locker\\MaterRoot";
    public static void main(String[] args) {
        
        Data data = EncryptedFileSystem.init(forPC);
        if(data==null)
        {
            Data.create("Wow", 15);
            Data.Construct(Root.masterRoot);
        }
        boolean result = EncryptedFileSystem.checkPassword("Okay");
        System.out.println(result);
    }
    
}
