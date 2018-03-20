/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileSystem;

import static FileSystem.Root.masterPath;
import Security.UniqueID;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import encrypted.file.system.EncryptedFileSystem;

/**
 *
 * @author costa
 */
public class File implements Serializable{
    public static File recentFile;
    
    public Root root;
    public String name;
    private String data;
    private String[] words;
    public transient UniqueID uuid;
    private byte[] iv;
    
    private transient java.io.File myFile;
    private File(Root root, String name)
    {
        this.root=root;
        this.name=name;
        this.uuid = new UniqueID();
        myFile = new java.io.File(myPath());
        WriteToFile(this, this);
    }
    private File(Root root, String name, String data, boolean decrypted)
    {
        this(root,name);
        this.data=data;
        if(decrypted) words = data.split(" ");
        myFile = new java.io.File(myPath());
        WriteToFile(this, this);
        
    }
    public static File newFile(Root root, String name)
    {
        recentFile = new File(root, name);
        root.addFile(recentFile);
        return recentFile;
    }
    public static File newFile(Root root, String name, String data, boolean decrypted)
    {
        recentFile = new File(root, name, data, decrypted);
        root.addFile(recentFile);
        return recentFile;
    }
    
    public static String Write(String path, String data)
    {
        try 
        {
            
            PrintWriter p = new PrintWriter(path);
            p.print(data);
            p.close();
            
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    public String reWrite(String data)
    {
        return Write(myPath(), data);
    }
    
    
    
    public String[] setWords()
    {
        return (words = data.split(" "));
    }
    private String retrace;
    private boolean okies = false;
    public String myPath()
    {
        okies=false;
        retrace= "";
        myPath2(root);
        retrace+="\\"+this.name;
        return retrace;
    }
    private String myPath2(Root root)
    {
        if(!okies&&root.name.contentEquals("MasterRoot"))
        {
            okies=true;
            retrace+= masterPath+"\\MasterRoot";
            return root.name;
        }
        else
        {
           myPath2(root.Parent);
           return (retrace+= "\\"+root.name);
        }
    }
    public void setIV(byte[] iv)
    {
        this.iv=iv;
    }
    public byte[] setData(byte[] string)
    {
        return (this.data = new String(string)).getBytes();
    }
    public String setData(String string)
    {
        return (this.data = string);
    }
    public String getData()
    {
        return this.data;
    }
    public byte[] getIV()
    {
        return this.iv;
    }
    public java.io.File myFile()
    {
        return this.myFile;
    }
    public java.io.File setFile(java.io.File ActualFile)
    {
        return this.myFile=ActualFile;
    }
    public void WriteFile()
    {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(myPath());
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
        } catch (FileNotFoundException ex) {
            try {
                PrintWriter p = new PrintWriter(myPath());
                p.print("");
                p.close();
            } catch (FileNotFoundException ex1) {
                Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static Object WriteToFile(Object o, File f)
    {
        FileOutputStream fout = null;
        try {
            System.out.println(f.myPath());
            fout = new FileOutputStream(f.myPath());
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(o);
        } catch (FileNotFoundException ex) {
            try {
                PrintWriter p = new PrintWriter(f.myPath());
                p.print("");
                p.close();
            } catch (FileNotFoundException ex1) {
                java.io.File file = new java.io.File(f.myPath());
                WriteToFile(o,f);
            }
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return o;
    }
    public static Object ReadFromFile(java.io.File f)
    {
        Object o = null;
        FileInputStream fout = null;
        ObjectInputStream oos = null;
        try {
            fout = new FileInputStream(f.getAbsolutePath());   
            oos = new ObjectInputStream(fout);
            o = oos.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fout.close();
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return o;
    }
        
            
}
