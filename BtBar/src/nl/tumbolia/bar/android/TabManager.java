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

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public final class TabManager
{
	private TabManager()
	{
		// Utility class
	}

	public static void initialize(Activity activity, int defaultIndex,
			int[] nameIDs, int[] fragmentIDs)
	{
		ActionBar actionBar = prepareAndGetActionBar(activity, fragmentIDs);

		for (int i = 0; i < nameIDs.length; i++)
		{
			TabListener tabListener = (TabListener) activity
					.getFragmentManager().findFragmentById(fragmentIDs[i]);
			Tab t = actionBar.newTab().setText(nameIDs[i]).setTag(tabListener)
					.setTabListener(tabListener);
			// TODO t.setIcon(icon)
			actionBar.addTab(t);
		}
		actionBar.getTabAt(defaultIndex).select();
	}

	private static ActionBar prepareAndGetActionBar(Activity activity,
			int[] fragmentIDs)
	{
		ActionBar actionBar = activity.getActionBar();
		if (fragmentIDs.length > 0
				&& findFragmentById(activity, fragmentIDs[0]) != null)
		{
			actionBar.removeAllTabs();
		}
		return actionBar;
	}

	private static TabListener findFragmentById(Activity activity,
			int fragmentID)
	{
		return (TabListener) activity.getFragmentManager().findFragmentById(
				fragmentID);
	}

	public static void loadTabFragments(Activity activity, Bundle data)
	{
		int n = activity.getActionBar().getTabCount();
		if (0 != n)
		{
			doLoad(activity, n, data);
		} 
		else
		{
			activity.startActivity(new Intent(activity, BTBarActivity.class)
					.putExtras(data));
		}
	}

	public static void selectTab(Activity activity, int tabNumber)
	{
		ActionBar actionBar = activity.getActionBar();
		actionBar.selectTab(actionBar.getTabAt(tabNumber));
	}

	private static void doLoad(Activity activity, int n, Bundle data)
	{
		ActionBar actionBar = activity.getActionBar();
		actionBar.selectTab(actionBar.getTabAt(0));
	}

}
