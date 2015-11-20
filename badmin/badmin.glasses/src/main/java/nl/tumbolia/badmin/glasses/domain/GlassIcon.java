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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonValue;

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
    private Long id;

    @Lob
    @Column(nullable=false)
    private byte[] data;
    
    @Transient
    private String location;

    public GlassIcon()
    {
        // Default constructor
    }

    public GlassIcon(final byte[] data)
    {
        this.data = data;
    }
    
    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public byte[] getData()
    {
        return data;
    }

    public void setData(final byte[] data)
    {
        this.data = data;
    }

    @JsonValue
    public String getLocation()
    {
        return location;
    }

    public void setLocation(final String location)
    {
        this.location = location;
    }
    
    @Transient
    public boolean isNew()
    {
        return id == null;
    }
}
