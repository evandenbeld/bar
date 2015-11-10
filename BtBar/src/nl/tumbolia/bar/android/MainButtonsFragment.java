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

import java.util.List;

import javax.annotation.Nullable;

import nl.tumbolia.bar.android.bluetooth.BlueToothConnectedhandler;
import nl.tumbolia.bar.android.bluetooth.DeviceListActivity;
import nl.tumbolia.bar.android.bluetooth.bartender.BtBartender;

import org.roboguice.shaded.goole.common.base.Predicate;
import org.roboguice.shaded.goole.common.collect.ImmutableList;
import org.roboguice.shaded.goole.common.collect.Iterators;

import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.inject.Inject;

public class MainButtonsFragment extends RoboFragment implements OnClickListener, TabListener
{			
	@Inject
	private BtBartender bartender;
	
	@InjectView(R.id.bluetoothButton)
	private Button bluetoothButton;
	
	@InjectView(R.id.distributionButton1)
	private DistributionButton distributionButton1;
	@InjectView(R.id.distributionButton2)
	private DistributionButton distributionButton2;
	@InjectView(R.id.distributionButton3)
	private DistributionButton distributionButton3;
	@InjectView(R.id.distributionButton4)
	private DistributionButton distributionButton4;
	
	private List<DistributionButton> distributionButtons;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)	
	{
		//TODO @ContentView does not work somehow
		super.onCreateView(inflater, container, savedInstanceState);
		final View buttonsView = inflater.inflate(R.layout.main_buttons, container, false);
			
		return buttonsView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{	
		super.onViewCreated(view, savedInstanceState);
		
		distributionButtons = ImmutableList.of(distributionButton1, distributionButton2, distributionButton3, distributionButton4);
		
		initBluetoothButton();
		initDistributionButtons();
		
		registerConnectionListener(distributionButtons,	bluetoothButton);
	}

	private void initDistributionButtons()
	{
		for(int i=0;i<distributionButtons.size();i++)
		{
			distributionButtons.get(i).setOnClickListener(this);
			distributionButtons.get(i).setDistributor(bartender.getDistributors().get(i));
			distributionButtons.get(i).setVisibility(View.GONE);
		}			
	}

	private void initBluetoothButton()
	{
		bluetoothButton.setOnClickListener(this);
	}

	private void registerConnectionListener(final List<DistributionButton> activeOnConnectionButtons, final Button connectButton)
	{
		bartender.registerBtHelperHandler(new BlueToothConnectedhandler()
		{			
			@Override
			protected void connectionStateChanged(final boolean connected)
			{
				connectButton.setVisibility(connected ? View.GONE : View.VISIBLE);
				for(Button button : activeOnConnectionButtons)
				{
					button.setVisibility(connected ? View.VISIBLE : View.GONE);
				}								
			}
		});
	}

	@Override
	public void onClick(final View sourceButton)
	{				
		if(sourceButton.getId() == R.id.bluetoothButton)
		{
			Intent serverIntent = new Intent(super.getActivity(), DeviceListActivity.class);
			getActivity().startActivityForResult(serverIntent, BTBarActivity.REQUEST_CONNECT_DEVICE);
		}	
		
		onDistributionClick(sourceButton);		
	}

	private void onDistributionClick(final View sourceButton)
	{
		int distributorIndex = Iterators.<DistributionButton>indexOf(distributionButtons.iterator(), new Predicate<Button>()
		{
			@Override
			public boolean apply(@Nullable Button button)
			{
				return button != null && button.getId() == sourceButton.getId();
			}
			
		});
		if(distributorIndex > 0)
		{
			bartender.getDistributors().get(distributorIndex).pour();
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// Do nothing
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.show(this);
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.hide(this);		
	}
}