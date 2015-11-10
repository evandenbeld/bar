/*
 * 
 * 							   Bluetooth Bar App
 * 								BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.bar.android.cocktails;

import nl.tumbolia.bar.android.R;
import nl.tumbolia.bar.android.bluetooth.bartender.BtBartender;
import nl.tumbolia.bar.android.cocktails.dao.Cocktail;
import nl.tumbolia.bar.android.cocktails.dao.CocktailsDao;

import org.roboguice.shaded.goole.common.base.Optional;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * TODO javadoc
 * 
 * @author TODO erwin
 * @version TODO
 */
//FIXME @ContentView(R.layout.cocktail_activity)
public class CocktailDetailActivity extends RoboActivity
{
	@Inject
	private BtBartender bartender;
	
	@Inject CocktailsDao cocktailsDao;
	
	@InjectView(R.id.cocktailDetailTitle)
	TextView cocktailTitle;
	
	@InjectView(R.id.cocktailDetailImage)
	ImageView cocktailImage;
	
	@InjectView(R.id.cocktailDetailDescription)
	TextView cocktailDescription;
	
	@InjectView(R.id.cockTailDetailRecipe)
	TextView cocktailRecipe;
	
	//FIXME 2  ingredients
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cocktail_activity);
		
		Long cocktailId = (Long)getIntent().getExtras().get("cocktailId");
		if(cocktailId != null)
		{		
			try
			{
				cocktailsDao.open();
			
				Optional<Cocktail> cocktail = cocktailsDao.getCocktail(cocktailId);
				if(cocktail.isPresent())
				{
					cocktailTitle.setText(cocktail.get().getName());
					cocktailImage.setImageDrawable(getResources().getDrawable(CocktailsDao.getImageResourceIdByGlassName(cocktail.get().getGlass().getName())));
					cocktailDescription.setText(cocktail.get().getDescription());
					cocktailRecipe.setText(cocktail.get().getRecipe());
				}
			}
			finally
			{
				cocktailsDao.close();
			}
		}
		
	}
	
	//FIXME button including state listener + pour correct valves
	
}
