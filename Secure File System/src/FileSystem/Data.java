/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileSystem;

import Security.SHA256;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import secure.file.system.SecureFileSystem;

/**
 *
 * @author costa
 */
public class Data implements Serializable{
    
    public byte[] salt;
    public byte[] check;
    
    public static transient ArrayList<File> Files = new ArrayList();
    public static transient ArrayList<Root> Directories = new ArrayList();
    
    public Data(String salt, String check)
    {
        this.salt=salt.getBytes();
        this.check=check.getBytes();
        System.out.println("Salt: "+salt);
        System.out.println("Check: "+check);
    }
    public String setSalt(String newSalt)
    {
        return new String((this.salt=newSalt.getBytes()));
    }
    public String setCheck(String newCheck)
    {
        return new String((this.check=newCheck.getBytes()));
    }
    public byte[] getSalt()
    {
        return this.salt;
    }
    public byte[] getCheck()
    {
        return this.check;
    }
    public static Data Startup(java.io.File folder)
    {
        Data myData =null;
        java.io.File salts = new java.io.File((Root.masterPath+"\\MasterRoot\\salts"));
        System.out.println(Root.masterPath);
        if(salts.exists())
        {
            System.out.println("Salts found, loading into data...");
            myData = (Data)File.ReadFromFile(salts);
        }
        else
        {
            System.out.println("Salts not found, creating data...");
            myData = new Data(new String(new SecureRandom().generateSeed(8)), SHA256.Encrypt(SecureFileSystem.password));
            File.WriteToFile(myData, File.newFile(Root.masterRoot, "salts"));
            
        }
        return myData;
    }
    public static File returnFileByName(String name)
    {
        for(File f:Files)
        {
            if(f.name.contentEquals(name))
                return f;
        }
        return null;
    }
    public static Root returnRootByName(String name)
    {
        for(Root r:Directories)
        {
            if(r.name.contentEquals(name))
                return r;
        }
        return null;
    }
    public static void newConstruct(Root root)
    {
        Files.clear();
        Directories.clear();
        Construct(root);
    }
    public static void Construct(Root root)
    {
        java.io.File[] directories = root.myFile.listFiles();
        for(java.io.File f:directories)
        {
            if(f.isFile()&&(!f.getName().contentEquals("salts")))
            {
                Files.add(((File)File.ReadFromFile(f)));
            }
            else if(f.isDirectory())
            {
                Directories.add(Root.NewRoot(f.getName(), root));
            }
        }
    }
}
