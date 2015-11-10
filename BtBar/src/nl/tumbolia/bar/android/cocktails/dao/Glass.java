/*
 * 
 * 							   Bluetooth Bar App
 * 								BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.bar.android.cocktails.dao;

import android.content.ContentValues;

/**
 * TODO javadoc
 * 
 * @author TODO erwin
 * @version TODO
 */
public class Glass implements ContentValuesProvider
{
	public static final String TABLE_NAME = "glasses";	
	public static final String NAME = "name";
	public static final String CONTENTS = "contents";
	
	private long id;
	private String name;
	private int  contents;
	
	public Glass()
	{
	}
	
	public Glass(String name, int contents)
	{
		this.name = name;
		this.contents = contents;
	}

	public Glass(long id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getContents()
	{
		return contents;
	}
	
	public void setContents(int contents)
	{
		this.contents = contents;
	}
	
	public long getId()
	{
		return id;
	}

	@Override
	public void setId(long id)
	{
		this.id = id;		
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	
	@Override
	public ContentValues toContentValues()
	{
		final ContentValues contentValues = new ContentValues(2);
		contentValues.put(NAME, name);
		contentValues.put(CONTENTS, contents);
		return contentValues;		
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
		if (id != other.id)
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return String.format("%s (%d ml)", name, contents);
	}	
}
