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

/**
 * TODO javadoc
 * 
 * @author TODO erwin
 * @version TODO
 */
public abstract class CocktailItem
{
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";

	private long _id;
	private String name;
	private String description;

	public CocktailItem()
	{
	}

	public CocktailItem(long _id)
	{
		this._id = _id;
	}

	public CocktailItem(String name, String description)
	{
		this.name = name;
		this.description = description;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}	

	public long getId()
	{
		return _id;
	}
		
	public void setId(long id)
	{
		_id = id;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (_id ^ (_id >>> 32));
		return result;
	}	

	@Override
	public String toString()
	{
		return name;
	}
}
