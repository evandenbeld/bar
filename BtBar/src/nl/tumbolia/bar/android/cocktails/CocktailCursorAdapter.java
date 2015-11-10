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
import nl.tumbolia.bar.android.cocktails.dao.CocktailItem;
import nl.tumbolia.bar.android.cocktails.dao.CocktailsDao;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TODO javadoc
 * 
 * @author TODO erwin
 * @version TODO
 */
public class CocktailCursorAdapter extends CursorAdapter
{				
	public CocktailCursorAdapter(Context context, Cursor c, int flags)
	{
		super(context, c, flags);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{
		return LayoutInflater.from(context).inflate(R.layout.cocktail, parent, false);
	}

	@Override
	public void bindView(View rowView, Context context, Cursor cursor)
	{
		if (rowView.getTag() == null) 
		{
		      ViewHolder viewHolder = new ViewHolder();
		      viewHolder.cocktailNameView = (TextView) rowView.findViewById(R.id.cocktailName);
		      viewHolder.cocktailDescriptionView = (TextView) rowView.findViewById(R.id.cocktailDescription);
		      viewHolder.cocktailIcon = (ImageView) rowView.findViewById(R.id.cocktailIcon);
		      rowView.setTag(viewHolder);
		 }
		
		final ViewHolder itemView = (ViewHolder)rowView.getTag();
//		final TextView cocktailNameView = (TextView) view.findViewById(R.id.cocktailName);
//		final TextView cocktailDescriptionView = (TextView) view.findViewById(R.id.cocktailDescription);

		final String glassname = cursor.getString(cursor.getColumnIndexOrThrow("glass_name"));
		itemView.cocktailIcon.setImageDrawable(context.getResources().getDrawable(CocktailsDao.getImageResourceIdByGlassName(glassname)));
		
		itemView.cocktailIcon.setContentDescription(cursor.getString(cursor.getColumnIndexOrThrow("glass_name")));
		itemView.cocktailNameView.setText(cursor.getString(cursor.getColumnIndexOrThrow("cocktail_name")));
		itemView.cocktailDescriptionView.setText(cursor.getString(cursor.getColumnIndexOrThrow(CocktailItem.DESCRIPTION)));

	}
	
	
	private static final class ViewHolder 
	{
	    public ViewHolder()
		{
		}
	    
		TextView cocktailNameView;
		TextView cocktailDescriptionView;
		ImageView cocktailIcon;
	  }

}
