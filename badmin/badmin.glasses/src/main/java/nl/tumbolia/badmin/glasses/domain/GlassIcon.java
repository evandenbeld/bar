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
import java.net.URI;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

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
    private long id;

    @Column(nullable = false, unique = true)
    private URI location;

    @Lob
    private byte[] data;

    public GlassIcon()
    {
        // Default constructor
    }

    public GlassIcon(URI location, byte[] data)
    {
        this.location = location;
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

    @JsonValue
    public URI getLocation()
    {
        return location;
    }

    public void setLocation(URI location)
    {
        this.location = location;
    }

    public byte[] getData()
    {
        return data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((location == null) ? 0 : location.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof GlassIcon))
        {
            return false;
        }
        GlassIcon other = (GlassIcon) obj;
        return Objects.equals(location, other.location);
    }

    @Override
    public String toString()
    {
        return location != null ? location.toString() : null;
    }

}
