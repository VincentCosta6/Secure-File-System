/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author costa
 */
public class UniqueID implements Serializable{
    
    private UUID uuid;
    public UniqueID()
    {
        this.uuid = UUID.randomUUID();
    }
    public UUID getUUID()
    {
        return uuid;
    }
    public String getID()
    {
        return uuid.toString();
    }
}
