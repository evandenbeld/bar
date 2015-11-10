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

import nl.tumbolia.bar.android.R;
import nl.tumbolia.bar.android.bluetooth.bartender.BtBartender;
import nl.tumbolia.bar.android.bluetooth.bartender.BtDistributor;
import roboguice.fragment.provided.RoboPreferenceFragment;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;

import com.google.inject.Inject;

/**
 * @author erwin
 *
 */
public class SettingsFragment extends RoboPreferenceFragment implements TabListener, OnSharedPreferenceChangeListener
{
	
	@Inject
	private BtBartender bartender;
	
	//FIXME add drank welke naar de DB verwijst met alle draken + preference met pull-down
	
    public static final String SHARED_PREFERENCES_AMOUNT_REGEX = "^[0-9]{1,3}$";
    
    public static final String SHARED_PREFERENCES_BT_MESSAGE = "pref_bt_message";
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences); 
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    
    
    @Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{	
		super.onViewCreated(view, savedInstanceState);
        
        
        initDistributorPreferences();
        
        
//		TODO validation conform:
//		String timeoutPreference = getpropertyAsPreference(id, "timeout");
//		sharedPreferences.findPreference(timeoutPreference).setOnPreferenceChangeListener(
//	    				   new ValidationPreferenceChangeListener(
//	    						   sharedPreferences.getActivity(), timeoutPreference));
       
    }        

	private void initDistributorPreferences()
	{		
        for(BtDistributor distributor : bartender.getDistributors())
        {        	
        	final SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        	
        	final Preference liquorPreference = findPreference(DistributorPreferenceUtils.createLiquorKey(distributor));
        	distributor.setLiquor(sharedPreferences.getString(liquorPreference.getKey(), liquorPreference.getKey()));
        	liquorPreference.setSummary(distributor.getLiquor());
        	
        	final Preference timeoutFromPreference = findPreference(DistributorPreferenceUtils.createTimeoutKey(distributor));
        	distributor.setTimeout(Integer.parseInt(sharedPreferences.getString(timeoutFromPreference.getKey(), "5")));
            timeoutFromPreference.setSummary("" + distributor.getTimeout());        	
        	
        	final Preference hardwareIdPreference = findPreference(DistributorPreferenceUtils.createhardwareIdkey(distributor));
        	distributor.setHardwareId(Integer.parseInt(sharedPreferences.getString(hardwareIdPreference.getKey(), "1")));
        	hardwareIdPreference.setSummary("" + distributor.getHardwareId());
        }
	}
    
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// Do nothing
		ft.show(this);
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.show(this);
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.hide(this);
		
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
	{
		findPreference(key).setSummary(sharedPreferences.getString(key, ""));
		DistributorPreferenceUtils.updateDistributorSharedPreferences(bartender.getDistributors(), sharedPreferences, key);
		
	}  
}