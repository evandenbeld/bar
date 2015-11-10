/*
 * 
 * 							   Bluetooth Bar Admin
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
public class Cocktail extends CocktailItem implements ContentValuesProvider
{
	// public static final Uri CONTENT_URI =
	// Uri.parse("content://nl.tumbolia.bar/cocktails");
	// public static final String CONTENT_TYPE =
	// "vnd.android.cursor.dir/vnd.nl.tumbolia.bar.cocktails";

	public static final String TABLE_NAME = "cocktails";
	public static final String RECIPE = "recipe";
	public static final String GLASS_ID = "glass_id";

	private String recipe;
	private Glass glass;

	public Cocktail()
	{
		super();
	}

	public Cocktail(long id)
	{
		super(id);
	}

	
	public Cocktail(String name, String description, String recipe, Glass glass)
	{
		super(name, description);
		this.recipe = recipe;
		this.glass = glass;
	}

	public String getRecipe()
	{
		return recipe;
	}

	public void setRecipe(String recipe)
	{
		this.recipe = recipe;
	}

	public Glass getGlass()
	{
		return glass;
	}

	public void setGlass(Glass glass)
	{
		this.glass = glass;
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
		if (!(obj instanceof Cocktail))
		{
			return false;
		}
		Cocktail other = (Cocktail) obj;
		if (getId() != other.getId())
		{
			return false;
		}
		return true;
	}

	@Override
	public ContentValues toContentValues()
	{
		final ContentValues contentValues = new ContentValues(4);
		contentValues.put(CocktailItem.NAME, getName());
		contentValues.put(CocktailItem.DESCRIPTION, getDescription());
		contentValues.put(Cocktail.GLASS_ID, glass == null ? null : glass.getId());
		contentValues.put(Cocktail.RECIPE, recipe);
		return contentValues;		
	}
}
