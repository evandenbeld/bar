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
public class Ingredient extends CocktailItem implements ContentValuesProvider
{
	public static final String TABLE_NAME = "ingredients";
	public static final String UNIT = "unit";
	
	private String unit;

	public Ingredient()
	{
		super();
	}

	public Ingredient(long id)
	{
		super(id);
	}	

	public Ingredient(String name, String description, String unit)
	{
		super(name, description);
		this.unit = unit;
	}

	public String getUnit()
	{
		return unit;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}
	
	@Override
	public ContentValues toContentValues()
	{
		final ContentValues contentValues = new ContentValues(3);
		contentValues.put(CocktailItem.NAME, getName());
		contentValues.put(CocktailItem.DESCRIPTION, getDescription());
		contentValues.put(Ingredient.UNIT, unit);
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
		if (!(obj instanceof Ingredient))
		{
			return false;
		}
		Ingredient other = (Ingredient) obj;
		if (getId() != other.getId())
		{
			return false;
		}
		return true;
	}
}
