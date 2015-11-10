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

import java.util.Map;

import nl.tumbolia.bar.android.R;

import org.roboguice.shaded.goole.common.base.Optional;
import org.roboguice.shaded.goole.common.collect.ImmutableList;
import org.roboguice.shaded.goole.common.collect.ImmutableMap;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;

/**
 * TODO javadoc
 * 
 * @author TODO erwin
 * @version TODO
 */
@ContextSingleton
public class CocktailsDao
{
	public static Map<String, Integer> iconMap = ImmutableMap.<String, Integer>builder().			
			put("Highball glass", R.drawable.highball).
			put("Lowball glass", R.drawable.lowball).
			put("Wine glass", R.drawable.wineglass).
			put("Cocktail glass", R.drawable.cocktailglass).
			put("Martini glass", R.drawable.martiniglass).
			put("Champagne flute", R.drawable.martiniglass).
			put("Shot glass (small)", R.drawable.shotglass).
			put("Shot glass (big)", R.drawable.shotglass).build();
	
	
	private static final String COCKTAIL_GLASS_JOIN = Cocktail.TABLE_NAME + " INNER JOIN " + Glass.TABLE_NAME;
	private static final String[] COCKTAIL_GLASS_PROJECTION = ImmutableList.<String>of(Cocktail.TABLE_NAME + "." + CocktailsSQLiteHelper.COLUMN_ID, Cocktail.TABLE_NAME + "." + CocktailItem.NAME + " AS cocktail_name", CocktailItem.DESCRIPTION, Cocktail.RECIPE, Glass.TABLE_NAME + "." + CocktailsSQLiteHelper.COLUMN_ID + " as glassid", Glass.TABLE_NAME + "." +  Glass.NAME + " AS glass_name", Glass.CONTENTS).toArray(new String[]{});
	private static final String COCKTAIL_GLASS_SELECTION = Cocktail.TABLE_NAME + "." + Cocktail.GLASS_ID + " = glassid";
	private static final String SINGLE_COCKTAIL_GLASS_SELECTION = Cocktail.TABLE_NAME + "." + Cocktail.GLASS_ID + " = glassid AND " + Cocktail.TABLE_NAME + "." + CocktailsSQLiteHelper.COLUMN_ID + "=?";
	
	private SQLiteDatabase database;
	private CocktailsSQLiteHelper dbHelper;	
	
	@Inject
	public CocktailsDao(final Context context)
	{
		dbHelper = new CocktailsSQLiteHelper(context);
	}
	
	public static Integer getImageResourceIdByGlassName(String glassname)
	{
		Integer cocktailIconId = iconMap.get(glassname);
		if(cocktailIconId == null)
		{
			cocktailIconId = R.id.cocktailIcon;
		}
		return cocktailIconId;
	}

	public void open() throws SQLException
	{
		database = dbHelper.getReadableDatabase();
	}

	public void close()
	{
		dbHelper.close();
	}

	public Cursor findAllCocktails()
	{
		return database.query(true, COCKTAIL_GLASS_JOIN, COCKTAIL_GLASS_PROJECTION, COCKTAIL_GLASS_SELECTION, null, null, null, null, null);
	}
	
	public Optional<Cocktail> getCocktail(long id)
	{		
		Optional<Cocktail> cocktail = Optional.<Cocktail>absent();
		Cursor cocktailCursor = database.query(true, COCKTAIL_GLASS_JOIN, COCKTAIL_GLASS_PROJECTION, SINGLE_COCKTAIL_GLASS_SELECTION, new String[]{Long.toString(id)}, null, null, null, null);
		if(cocktailCursor != null)
		{
			cocktailCursor.moveToFirst();
			cocktail = Optional.of(cursorToCocktail(cocktailCursor));
			cocktailCursor.close();
		}
		
		return cocktail;
	}
	
	 private Cocktail cursorToCocktail(Cursor cursor) 
	 {
		    Cocktail cocktail = new Cocktail(cursor.getLong(0));
		    cocktail.setName(cursor.getString(1));
		    cocktail.setDescription(cursor.getString(2));
		    cocktail.setRecipe(cursor.getString(3));
		    
		    Glass glass = new Glass(cursor.getLong(4));
		    glass.setName(cursor.getString(5));
		    glass.setContents(cursor.getInt(6));
		    cocktail.setGlass(glass);
		    return cocktail;
		  }

}
