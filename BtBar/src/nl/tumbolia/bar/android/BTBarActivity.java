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

import java.io.UnsupportedEncodingException;

import nl.tumbolia.bar.android.bluetooth.BtMessageHandler;
import nl.tumbolia.bar.android.bluetooth.DeviceListActivity;
import nl.tumbolia.bar.android.bluetooth.bartender.BtBartender;
import nl.tumbolia.bar.android.bluetooth.bartender.BtState;
import roboguice.RoboGuice;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.google.inject.Inject;

@ContentView(R.layout.btbar_activity)
public class BTBarActivity extends RoboActivity
{
	static
	{
		// TODO annotationDatabases using roboblend?
		RoboGuice.setUseAnnotationDatabases(false);
	}

	static final String TAG = BTBarActivity.class.getSimpleName();

	public static final int REQUEST_CONNECT_DEVICE = 1;
	public static final int REQUEST_ENABLE_BT = 2;

	private String mConnectedDeviceName = null;
	private BluetoothAdapter mBluetoothAdapter = null;

	@Inject
	private BtBartender bartender;
	

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		initTitleBar();

		// Get the Bluetooth adapter, only one for now
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null)
		{
			Toast.makeText(this, "Bluetooth is not available",
					Toast.LENGTH_LONG).show();
			finish();
			return;
		}

	}

	private void initTitleBar()
	{

		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ActionBar actionBar = getSupportActionBar();
		// actionBar.hide();

		// Set up the custom title
		// mTitle = (TextView) findViewById(R.id.title_left_text);
		// mTitle.setText(R.string.app_name);
		// mTitle = (TextView) findViewById(R.id.title_right_text);

		ActionBar bar = getActionBar();
		bar.setDisplayShowTitleEnabled(true);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//TODO eerste keer selecteren settings tab werkt niet.
		int names[] = { R.string.action_main, R.string.action_cocktail,
				R.string.action_settings };
		int fragments[] = { R.id.mainTab, R.id.cocktailTab, R.id.preferencesTab };
		TabManager.initialize(this, 0, names, fragments);

		Bundle b = getIntent().getExtras();
		TabManager.loadTabFragments(this, b);

		bar.show();
	}

	@Override
	public void onStart()
	{
		super.onStart();

		if (!mBluetoothAdapter.isEnabled())
		{
			startActivityForResult(new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
		} 
		else
		{
			bartender.registerBtHelperHandler(bartenderMessageHandler);
		}
	}

	@Override
	public synchronized void onResume()
	{
		super.onResume();

		if (bartender.getState() == BtState.NONE)
		{
			bartender.start();
		}
	}
	

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if (bartender != null)
		{
			bartender.stop();
		}

	}	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
		case REQUEST_CONNECT_DEVICE:
			handdleDeviceListActivityReturnsDeviceToConnect(resultCode, data);
			break;
		case REQUEST_ENABLE_BT:
			handleBlueToothEnableResponse(resultCode);
		}
	}


	private void handdleDeviceListActivityReturnsDeviceToConnect(int resultCode,
			Intent data)
	{
		if (resultCode == Activity.RESULT_OK)
		{
			String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
			BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
			bartender.connect(device);
		}
	}
	
	private void handleBlueToothEnableResponse(int resultCode)
	{
		if (resultCode == Activity.RESULT_OK)
		{
			bartender.registerBtHelperHandler(bartenderMessageHandler);
		} 
		else
		{
			Log.d(TAG, "BT not enabled");
			Toast.makeText(this, R.string.bt_not_enabled_leaving,
					Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return false;
	}

	private final BtMessageHandler bartenderMessageHandler = new BtMessageHandler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			BtMessageHandler.MessageType messageType = BtMessageHandler.MessageType.values()[msg.what];
			switch (messageType)
			{
			case STATE:
				stateChanged((BtState) msg.obj);
				break;
			case WRITE:
				writeToBar(msg);
				break;
			case READ:
				handleBarResponse(msg);
				break;
			case DEVICE:
				setDeviceName((String) msg.obj);				
				break;
			case NOTIFY:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
		
		private void stateChanged(BtState state)
		{
			switch (state)
			{
			case CONNECTED:
				setTitle(R.string.title_connected_to);
				break;
			case CONNECTING:
				setTitle(R.string.title_connecting);
				break;
			case LISTEN:
				setTitle(R.string.title_listen);
				break;
			case NONE:
				setTitle(R.string.title_not_connected);
				break;
			}
		}

		public void writeToBar(Message msg)
		{
			byte[] writeBuf = (byte[]) msg.obj;
			String writeMessage = new String(writeBuf);
			Log.d(TAG, writeMessage);
		}

		private void handleBarResponse(final Message msg)
		{
			try
			{
				String readMessage = new String((byte[]) msg.obj, 0, msg.arg1, "UTF-8");
				Log.d(TAG, readMessage);
				if(!"OKAY".equals(readMessage))
				{
					Toast.makeText(getApplicationContext(), readMessage,
							Toast.LENGTH_SHORT).show();
				}
			} 
			catch (UnsupportedEncodingException e)
			{
				Log.e(TAG, e.getMessage(), e);
			}			
		}
		
	};
	
	void setDeviceName(final String devicename)
	{
		mConnectedDeviceName = devicename;
		Toast.makeText(getApplicationContext(),	"Connected to " + mConnectedDeviceName,
				Toast.LENGTH_SHORT).show();
	}

}
