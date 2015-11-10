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
import nl.tumbolia.bar.android.cocktails.dao.CocktailsDao;
import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.inject.Inject;

public class OrderCocktailFragment extends RoboFragment implements OnSharedPreferenceChangeListener, TabListener, OnItemClickListener
{
	private static final String TAG = "OrderCocktailFragment";
		
	@InjectView(R.id.cocktailListView)
	private ListView cocktailListView;
	
	@Inject
	private CocktailsDao cocktailsDao;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle saveState)
	{
		
//	FIXME inject?	final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
		return inflater.inflate(R.layout.cocktails, container, false);	
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{	
		super.onViewCreated(view, savedInstanceState);
		
		cocktailsDao.open();		
		final CocktailCursorAdapter adapter = new CocktailCursorAdapter(getActivity(), cocktailsDao.findAllCocktails(), 0);
		cocktailListView.setAdapter(adapter);
		cocktailsDao.close();
		
		cocktailListView.setOnItemClickListener(this);		
	}


	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key)
	{		
		//FIXME 3 add + reload filter
	}


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) 
	{
		// Do nothing
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) 
	{
		ft.show(this);
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) 
	{
		ft.hide(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 	
	{
		final Intent intent = new Intent(parent.getContext(), CocktailDetailActivity.class);
		intent.putExtra("cocktailId", id);		
	    startActivity(intent);		
	}  
}
