/*
 * 
 * Bluetooth Bar Admin
 * BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.badmin.glasses.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * Simple cocktail glass.
 * 
 * @author erwin
 */
@Entity(name = "GLASSES")
public class Glass implements Serializable
{
    private static final long serialVersionUID = 4015984755446343646L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GLASS_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int contents;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    private GlassIcon icon;

    public Glass()
    {
        // Default constructor
    }

    public Glass(final String name, final int contents)
    {
        this.name = name;
        this.contents = contents;
        this.unit = Unit.MILLI_LITER;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public int getContents()
    {
        return contents;
    }

    public void setContents(final int contents)
    {
        this.contents = contents;
    }

    public Unit getUnit()
    {
        return unit;
    }

    public void setUnit(final Unit unit)
    {
        this.unit = unit;
    }

    public GlassIcon getIcon()
    {
        return icon;
    }

    public void setIcon(final GlassIcon icon)
    {
        this.icon = icon;
    }
    
    @Transient
    public boolean isNew()
    {
        return id == null;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        if (!(obj instanceof Glass))
        {
            return false;
        }
        Glass other = (Glass) obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public String toString()
    {
        return String.format("%d: %s (%s%s)", id, name, contents, unit);
    }

}