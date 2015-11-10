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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.roboguice.shaded.goole.common.collect.ImmutableList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * TODO javadoc
 * 
 * @author TODO erwin
 * @version TODO
 */
public class CocktailsSQLiteHelper extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "cocktails.db";
	private static final int DATABASE_VERSION = 5;
	public static final String COLUMN_ID = "_id";		

	private static final String CREATE_GLASSES_TABLE = "CREATE TABLE "
			+ Glass.TABLE_NAME + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + Glass.NAME
			+ " TEXT NOT NULL, " + Glass.CONTENTS + " INTEGER NOT NULL);";
	
	private static final String CREATE_COCKTAILS_TABLE = "CREATE TABLE "
			+ Cocktail.TABLE_NAME + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + CocktailItem.NAME
			+ " TEXT NOT NULL, " + CocktailItem.DESCRIPTION + " TEXT, "
			+ Cocktail.RECIPE + " TEXT, " + Cocktail.GLASS_ID
			+ " INTEGER NOT NULL, "
			+ "FOREIGN KEY(glass_id) REFERENCES glasses(_id));";	
		
	private static final String CREATE_INGREDIENTS_TABLE = "CREATE TABLE "
			+ Ingredient.TABLE_NAME + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + CocktailItem.NAME
			+ " TEXT NOT NULL, " + CocktailItem.DESCRIPTION + " TEXT, "
			+ Ingredient.UNIT + " TEXT);";				
	
	private static final String CREATE_COCKTAIL_INGREDIENTS_TABLE = 
			"CREATE TABLE cocktail_ingredients(_id  INTEGER PRIMARY KEY AUTOINCREMENT, cocktail_id INTEGER NOT NULL, ingredient_id INTEGER NOT NULL, " +	
					"CONSTRAINT ci1 UNIQUE (cocktail_id, ingredient_id)" + 
					"FOREIGN KEY(cocktail_id) REFERENCES " + Cocktail.TABLE_NAME + "(" + COLUMN_ID + ")," +
					"FOREIGN KEY(ingredient_id) REFERENCES " + Ingredient.TABLE_NAME + "(" + COLUMN_ID + "));";
	
	private static final String INSERT_INTO_COCKTAIL_INGREDIENTS = "INSERT INTO cocktail_ingredients(cocktail_id, ingredient_id) values(?,?);";
		
	public CocktailsSQLiteHelper(final Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(final SQLiteDatabase database)
	{
		database.execSQL(CREATE_GLASSES_TABLE);
		database.execSQL(CREATE_COCKTAILS_TABLE);
		database.execSQL(CREATE_INGREDIENTS_TABLE);
		database.execSQL(CREATE_COCKTAIL_INGREDIENTS_TABLE);
		
		//TODO these initial filling should be replace by some external resource (using a content provider facade)
		final List<Glass> glasses = Arrays.asList(new Glass[]{
				new Glass("Highball glass", 400), 
				new Glass("Lowball glass", 300),
				new Glass("Wine glass", 300),
				new Glass("Cocktail glass", 250),
				new Glass("Champagne flute", 200),
				new Glass("Martini glass", 250),
				new Glass("Shot glass (small)", 25),
				new Glass("Shot glass (big)", 50 ),
				new Glass("Champagne saucer", 300),
				new Glass("Brandy snifter", 350),
				new Glass("Stein glass", 300),
				new Glass("Mug", 500)});
		insert(database, Glass.TABLE_NAME, glasses);
		
		final List<Ingredient> ingredients = Arrays.asList(new Ingredient[]{
				new Ingredient("Vodka", "Distilled beverage composed primarily of water and ethanol, sometimes with traces of impurities and flavorings. Traditionally, vodka is made by the distillation of fermented cereal grains or potatoes, though some modern brands use other substances, such as fruits or sugar.", "ml"), 
				new Ingredient("Whiskey", "Whiskey is a spirit, aged in wood, obtained from the distillation of a fermented mash of grain.", "ml"),
				new Ingredient("White Rum", "Rum is a distilled alcoholic beverage made from sugarcane byproducts, such as molasses, or directly from sugarcane juice, by a process of fermentation and distillation.", "ml"),
				new Ingredient("Pisang Ambon", "A brand of Dutch liqueur. It has a dominating banana flavour, with additional tropical fruit nuances, and a bright green colour.", "ml"),
				new Ingredient("Blue Curaçao", "Curaçao is a liqueur flavored with the dried peel of the laraha citrus fruit, grown on the island of Curaçao.", "ml"),
				new Ingredient("Gin", "A spirit which derives its predominant flavour from juniper berries (Juniperus communis).", "ml"),
				new Ingredient("Tequila", "A distilled beverage made from the blue agave plant. Although tequila is a kind of mezcal, modern tequila differs somewhat in the method of its production, in the use of only blue agave plants, as well as in its regional specificity.", "ml"),
				new Ingredient("Orange juice", "Fruit juice obtained from squeezing orange, or from a can.", "ml"),
				new Ingredient("Pineapple juice", "Fruit juice obtained from a pineapple.", "ml"),
				new Ingredient("Coconut milk", "Milk obtained from a coconut", "ml"),
				new Ingredient("Carbonated water", "Also known as club soda, soda water, sparkling water, seltzer water, or fizzy water. Water into which carbon dioxide gas under pressure has been dissolved.", "ml"),
				new Ingredient("Triple Sec", "Triple sec, originally Curaçao triple sec, is a variety of Curaçao liqueur, an orange-flavoured liqueur made from the dried peels of bitter and sweet orange.", "ml")
		});
		insert(database, Ingredient.TABLE_NAME, ingredients);
		
		final List<Cocktail> cocktails = ImmutableList.of(
				new Cocktail("Piña colada", "The piña colada is a sweet cocktail made with rum, cream of coconut, and pineapple juice, usually served either blended or shaken with ice. It may be garnished with a pineapple wedge, a maraschino cherry, or both. The piña colada has been the national drink of Puerto Rico since 1978", "Mix with crushed ice in blender until smooth. Pour into chilled glass, garnish and serve", glasses.get(2)),
				new Cocktail("Screwdriver", "A screwdriver is a popular alcoholic highball drink made with orange juice and vodka. While the basic drink is simply the two ingredients, there are many variations; the most common one is made with one part vodka, one part of any kind of orange soda, and one part of orange juice. Many of the variations have different names in different parts of the world. The International Bartender Association has designated this cocktail as an IBA Official Cocktail.", "Mix in a highball glass with ice. Garnish and serve.", glasses.get(0)),
				new Cocktail("Kamikaze", "The Kamikaze is made of equal parts vodka, triple sec and lime juice. According to the International Bartenders Association, it is served straight up in a cocktail glass.", "Shake all ingredients together in a mixer with ice. Strain into glass, garnish and serve.", glasses.get(1)),
				new Cocktail("Bossprinter", "The super boswandeling", "Shake all ingredients together in a mixer with ice. Strain into glass.", glasses.get(6))			
				).asList();
		insert(database, Cocktail.TABLE_NAME, cocktails);
		
		final Map<Cocktail, List<Ingredient>> cocktailIngredients = new HashMap<Cocktail,List<Ingredient>>();
		cocktailIngredients.put(cocktails.get(0), Arrays.asList(new Ingredient[]{ingredients.get(3),ingredients.get(9) }));
		cocktailIngredients.put(cocktails.get(1), Arrays.asList(new Ingredient[]{ingredients.get(0),ingredients.get(7) }));
		cocktailIngredients.put(cocktails.get(2), Arrays.asList(new Ingredient[]{ingredients.get(0),ingredients.get(11) }));
		cocktailIngredients.put(cocktails.get(3), Arrays.asList(new Ingredient[]{ingredients.get(2),ingredients.get(3), ingredients.get(4),ingredients.get(9) }));
				
		insertCocktailIngredients(database, cocktailIngredients);		
	}
	
	private void insert(final SQLiteDatabase database, final String table, final List<? extends ContentValuesProvider> contentValuesProviders)
	{
		try
		{
			database.beginTransaction();			
			
			for(ContentValuesProvider contentValuesProvider : contentValuesProviders)
			{
				long id = database.insertOrThrow(table, null, contentValuesProvider.toContentValues());
				contentValuesProvider.setId(id);				
			}
			database.setTransactionSuccessful();
		}
		finally
		{
			database.endTransaction();
		}
	}
	
	private void insertCocktailIngredients(final SQLiteDatabase database, Map<Cocktail,List<Ingredient>> cocktailIngredients)
	{					
		SQLiteStatement insertStatement = null;
		try
		{
			database.beginTransaction();
			insertStatement = database.compileStatement(INSERT_INTO_COCKTAIL_INGREDIENTS);
			for(Entry<Cocktail, List<Ingredient>> entry : cocktailIngredients.entrySet())
			{
				insertStatement.bindLong(1, entry.getKey().getId());				
				for(Ingredient ingredient : entry.getValue())
				{
					insertStatement.bindLong(2, ingredient.getId());
					insertStatement.execute();
				}
			}			
			insertStatement.close();
			database.setTransactionSuccessful();
		}
		finally
		{
			database.endTransaction();
		}
	}


	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS cocktail_ingredients");
		db.execSQL("DROP TABLE IF EXISTS " + Ingredient.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Cocktail.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Glass.TABLE_NAME);
		
        onCreate(db);
		
	}
}
