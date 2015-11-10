/*
 * 
 * 							   Bluetooth Bar Admin
 * 								BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.bar.android.settings;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.tumbolia.bar.android.bluetooth.bartender.BtDistributor;
import android.content.SharedPreferences;

/**
 * @author erwin
 */
public final class DistributorPreferenceUtils
{
	private static final String HARDWARE_ID = "HardwareId";
	private static final String TIMEOUT = "Timeout";
	private static final String LIQUOR = "Liquor";
																		
	private static final Pattern SHARED_PREFERENCE_PATTERN = Pattern.compile("^distributionButton([0-9])([a-zA-Z_]+)$");

	private DistributorPreferenceUtils()
	{
		
	}	
	
	public static void updateDistributorSharedPreferences(final List<BtDistributor> distributors, final SharedPreferences sharedPreferences,
			String key)
	{
		final Matcher keyMatcher = SHARED_PREFERENCE_PATTERN.matcher(key);
		if(keyMatcher.matches())
		{
			final int index = Integer.parseInt(keyMatcher.group(1)) -1;
			final String property = keyMatcher.group(2);
			final BtDistributor distributor = distributors.get(index);
			if(property.equalsIgnoreCase(LIQUOR))
			{
				distributor.setLiquor(sharedPreferences.getString(key, LIQUOR));
			}
			else if(property.equalsIgnoreCase(TIMEOUT))
			{
				distributor.setTimeout(Integer.parseInt(sharedPreferences.getString(key, "5")));
			}
			else if(property.equalsIgnoreCase(HARDWARE_ID))
			{
				distributor.setHardwareId(Integer.parseInt(sharedPreferences.getString(key, "1")));
			}
		}						
		
	}	

	private static String createpropertyPreferenceKey(String property, int id)
	{
		return String.format(Locale.US, "distributionButton%d%s", id+1, property);
	}

	public static String createLiquorKey(final BtDistributor distributor)
	{
		return createpropertyPreferenceKey(LIQUOR, distributor.getId());
	}	

	public static String createTimeoutKey(final BtDistributor distributor)
	{
		return createpropertyPreferenceKey(TIMEOUT, distributor.getId());
	}	
	

	public static String createhardwareIdkey(final BtDistributor distributor)
	{
		return createpropertyPreferenceKey(HARDWARE_ID, distributor.getId());
	}	
}
