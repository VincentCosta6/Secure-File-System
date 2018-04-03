/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileSystem;

import Security.UniqueID;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author costa
 */
public class Root implements Serializable{
    public static ArrayList<Root> ROOTS;
    public static Root masterRoot;
    public static String masterPath;
    public static Root recentRoot;
    static{
        ROOTS = new ArrayList();
        masterPath = "D:\\Github\\Secure-File-System\\Locker\\MasterRoot";
        masterRoot = new Root("MasterRoot",null);
        
    }
    
    public ArrayList<File> files = new ArrayList();
    public ArrayList<Root> subRoots = new ArrayList();
    public String name;
    public UniqueID uuid;
    public Root Parent;
    public transient java.io.File myFile;
    public Root(String name, Root parent)
    {
        this.name=name;
        this.uuid=new UniqueID();
        this.Parent=parent;
        if(!name.contentEquals("MasterRoot"))
        {
            new java.io.File(myPath()).mkdirs();
            myFile = new java.io.File(myPath());
        }
        else
        {
            new java.io.File(masterPath).mkdirs();
            myFile = new java.io.File(masterPath);
        }
    }
    private Root(String name, ArrayList<File> files, Root parent)
    {
        this(name, parent);
        this.files=files;
    }
    public static Root NewRoot(String name, Root parent)
    {
        recentRoot = new Root(name, parent);
        parent.addSubRoot(recentRoot);
        return recentRoot;
    }
    public static Root NewRoot(String name, ArrayList<File> files, Root parent)
    {
        recentRoot = new Root(name,files, parent);
        parent.addSubRoot(recentRoot);
        return recentRoot;
    }
    
    public Root addSubRoot(Root root)
    {
        subRoots.add(root);
        return root;
    }
    public Root removeSubRoot(Root root)
    {
        subRoots.remove(root);
        return root;
    }
    public Root getSubRoot(String name, String uuid)
    {
        for(Root r:subRoots)
        {
            if(r.name.contentEquals(name)&&r.uuid.getID().contentEquals(uuid))
                return r;
        }
        return null;
    }
    
    
    public void addFile(File file)
    {
        files.add(file);
    }
    public File removeFile(File file)
    {
        if(files.contains(file))
        {
            files.remove(file);
            return file;
        }
        return null;
    }
    public File removeFile(String name, String uuid)
    {
        for(File f:files)
        {
            if(f.name.contentEquals(name)&&f.uuid.getID().contentEquals(uuid))
            {
                files.remove(f);
                return f;
            }
        }
        return null;
    }
    
    
    
    private String retrace;
    private boolean okies = false;
    public String myPath()
    {
        okies=false;
        retrace= "";
        myPath2(Parent);
        retrace+="\\"+this.name;
        System.out.println(retrace);
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
    public String toString()
    {
        String toSend = "Root: "+this.name+" Files: "+this.files.size()+"\n \n";
        for(Root sR: subRoots)
        {
            toSend+= "      SubRoot: " + sR.name + " Files: " + sR.files.size()+"\n";
        }
        toSend+="------------------------------------------------------------------------------------------";
        return toSend;
    }
}
