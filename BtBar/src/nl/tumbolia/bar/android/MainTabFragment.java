/*
 * 
 * 							   Bluetooth Bar App
 * 								BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.bar.android;

import android.R.color;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * TODO javadoc
 * 
 * @author TODO erwin
 * @version TODO
 */
public class MainTabFragment extends Fragment implements TabListener
{
//	private int animTime = 2000;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
//		animTime = getResources().getInteger(
//                android.R.integer.config_longAnimTime);

		final View view = inflater.inflate(R.layout.main, container, false);
		
		return view;
	}
	
	@Override
	public void onStart()
	{
		//TODO fadeOut(getView().findViewById(R.id.headerImage));
		super.onStart();
	}
		
	
//	private void fadeOut(final View view)
//	{
//		view.setVisibility(View.VISIBLE);
//		final Animation fadeOut = new AlphaAnimation(1, 0);
//	    fadeOut.setInterpolator(new AccelerateInterpolator());
//	    fadeOut.setDuration(animTime);
//
//	    fadeOut.setAnimationListener(new AnimationListener()
//	    {
//	            public void onAnimationEnd(Animation animation) 
//	            {
//	                  view.setVisibility(View.GONE);
//	            }
//
//				@Override
//				public void onAnimationRepeat(Animation animation)
//				{
//					//Nothing
//				}
//
//				@Override
//				public void onAnimationStart(Animation animation)
//				{
//					//Nothing
//				}	           
//	    });
//
//	    view.startAnimation(fadeOut);
//	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
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
		getView().setBackgroundResource(0);
		getView().setBackgroundColor(color.black);		
		ft.hide(this);
		
	}	
}
