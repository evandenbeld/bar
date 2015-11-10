/*
 * 
 * 							   Bluetooth Bar App
 * 								BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.bar.android.settings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;

public class ValidationPreferenceChangeListener implements OnPreferenceChangeListener
{	
	
	private Pattern pattern;
	private Context context;
	
	public ValidationPreferenceChangeListener(Context context, String regex)
	{
		this.pattern = Pattern.compile(regex);
		this.context = context;
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (newValue == null || newValue.toString() == null) {
			// TODO validation string messagea in xml
			builder.setTitle("Missing required Input for "  + preference.getKey());	
			builder.setMessage("Something's gone wrong...");
			builder.setPositiveButton(android.R.string.ok, null);
			builder.show();
			return false;
		}

		Matcher matcher = this.pattern.matcher(newValue.toString());
		if (!matcher.matches()) {
			builder.setTitle("Invalid Input for "  + preference.getKey());
			// TODO validation string message in xml
			builder.setMessage("Something's gone wrong...");
			builder.setPositiveButton(android.R.string.ok, null);
			builder.show();
			return false;
		}
		return true;
	}
	
	

}
