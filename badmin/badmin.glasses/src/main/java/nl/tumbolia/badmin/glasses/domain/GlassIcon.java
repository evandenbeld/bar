/*
 * Bluetooth Bar Admin
 * -------------------
 * BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.badmin.glasses.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Cocktail glass icon.
 * 
 * @author erwin
 */
@Entity(name = "GLASS_ICONS")
public class GlassIcon implements Serializable
{
    private static final long serialVersionUID = 9130413191523074452L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GLASS_ICON_ID")
    private long id;

    @Lob
    private byte[] data;

    public GlassIcon()
    {
        // Default constructor
    }

    public GlassIcon(byte[] data)
    {
        this.data = data;
    }
    
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public byte[] getData()
    {
        return data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }
}
