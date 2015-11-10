/*
 * 
 * 							    Bluetooth Bar App
 * 								BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 * 
 * Bluetooth code based on example code for O'Reilly's 
 * "Programming Android" by Zigured Mednieks, Laird Dornin, 
 * Blake Meike and Masumi Nakamura
 * 
 * https://github.com/bmeike/ProgrammingAndroidExamples/wiki
 */
package nl.tumbolia.bar.android.bluetooth.bartender;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nl.tumbolia.bar.android.R;
import nl.tumbolia.bar.android.bluetooth.BtMessageHandler;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton //FIXME ContextSingleton?
public class BtBartender
{	
	static final String TAG = BtBartender.class.getSimpleName();
	private static final boolean D = true;
	
	public static final UUID BARTENDER_BLUETOOTH_DEVICE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	public static final int NUMBER_OF_DISTRIBUTORS = 4;
	
	final BluetoothAdapter mAdapter;
	private final List<BtMessageHandler> handlers;
	private final List<BtDistributor> distributors;
	ConnectThread mConnectThread;
	private ConnectedThread mConnectedThread;
	private BtState state;
		
	private Context context;
	

	@Inject
	public BtBartender(final Context context)
	{			
		this.context = context;
		
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		state = BtState.NONE;		
		handlers = new ArrayList<BtMessageHandler>();
		distributors = createDistributors();		
	}

	public void registerBtHelperHandler(BtMessageHandler btMessageHandler)
	{
		if(!getHandlers().contains(btMessageHandler))
		{
			getHandlers().add(btMessageHandler);
		}
	}

	public synchronized BtState getState()
	{
		return state;
	}

	public List<BtDistributor> getDistributors()
	{
		return distributors;
	}

	public List<BtMessageHandler> getHandlers()
	{
		return handlers;
	}

	public synchronized void start()
	{
		if (D)
		{
			Log.d(TAG, "start");
		}

		cancelConnectAndConnectedThreads();
	}

	public synchronized void connect(BluetoothDevice device)
	{
		if (D)
		{
			Log.d(TAG, "connect to: " + device);
		}
	
		if (state == BtState.CONNECTING)
		{
			cancelAnyConnectionAttemptingThread();
		}
	
		cancelAnyRunningConnectionThread();
		startConnectThreadForDevice(device);
	
		setState(BtState.CONNECTING);
	}

	boolean distribute(final byte[] message)
	{
		ConnectedThread r;
	
		// Synchronize a copy of the ConnectedThread
		synchronized (this)
		{
			if (state != BtState.CONNECTED)
			{
				Log.w(TAG, "Not connected");
				return false;
			}
			r = mConnectedThread;
		}
		r.write(message);
		return true;
	}

	public synchronized void stop()
	{
		if (D)
		{
			Log.d(TAG, "stop");
		}
		cancelAllThreads();
		setState(BtState.NONE);
	}

	private synchronized void setState(BtState state)
	{
		if (D)
		{
			Log.d(TAG, "setState() " + state + " -> " + state);
		}
		this.state = state;
	
		for(BtMessageHandler handler : getHandlers())
		{
			handler.obtainMessage(BtMessageHandler.MessageType.STATE, -1, state).sendToTarget();
		}
	}

	private final List<BtDistributor> createDistributors()
	{		
		final List<BtDistributor> btDistributors = new ArrayList<BtDistributor>();		
		for(int i=0;i< NUMBER_OF_DISTRIBUTORS;i++)
		{
			BtDistributor distributor = new BtDistributor(this, i);			
			btDistributors.add(distributor);
		}
		return btDistributors;
	}

	private void cancelConnectAndConnectedThreads()
	{
		cancelAnyConnectionAttemptingThread();
		if (mConnectedThread != null)
		{
			cancelAnyRunningConnectionThread();
		}
	}

	private void startConnectThreadForDevice(BluetoothDevice device)
	{
		mConnectThread = new ConnectThread(device);
		mConnectThread.start();

	}

	private void cancelAnyRunningConnectionThread()
	{
		if (mConnectedThread != null)
		{
			mConnectedThread.cancel();
			mConnectedThread = null;
		}
	}

	private void cancelAnyConnectionAttemptingThread()
	{
		if (mConnectThread != null)
		{
			mConnectThread.cancel();
			mConnectThread = null;
		}
	}

	synchronized void connected(BluetoothSocket socket, BluetoothDevice device)
	{
		if (D)
		{
			Log.d(TAG, "connected");
		}

		cancelAllThreads();

		startTheConnectedThread(socket);
		sendConnectedDevicenameToUI(device);
		setState(BtState.CONNECTED);
	}

	private void sendConnectedDevicenameToUI(BluetoothDevice device)
	{
		for(BtMessageHandler handler : getHandlers())
		{
			handler.obtainMessage(BtMessageHandler.MessageType.DEVICE, -1,
					device.getName()).sendToTarget();
		}
	}

	private void startTheConnectedThread(BluetoothSocket socket)
	{
		mConnectedThread = new ConnectedThread(socket);
		mConnectedThread.start();
	}

	private void cancelAllThreads()
	{
		cancelConnectAndConnectedThreads();
	}

	void sendErrorMessage(int messageId)
	{
		setState(BtState.LISTEN);
		
		for(BtMessageHandler handler : getHandlers())
		{
			handler.obtainMessage(BtMessageHandler.MessageType.NOTIFY, -1,
					context.getResources().getString(messageId)).sendToTarget();
		}
	}

	void sendErrorMessage(String message)
	{
		setState(BtState.LISTEN);
		for(BtMessageHandler handler : getHandlers())
		{
			handler.obtainMessage(BtMessageHandler.MessageType.NOTIFY, -1, message).sendToTarget();
		}
	}

	private class ConnectThread extends Thread
	{
		private BluetoothSocket mmSocket;
		private final BluetoothDevice mmDevice;

		public ConnectThread(BluetoothDevice device)
		{
			mmDevice = device;
			BluetoothSocket tmp = null;

			// Get a BluetoothSocket for a connection with the
			// given BluetoothDevice
			try
			{
				tmp = device.createRfcommSocketToServiceRecord(BARTENDER_BLUETOOTH_DEVICE_UUID);
			} catch (IOException e)
			{
				Log.e(TAG, "create() failed", e);
			}
			mmSocket = tmp;
		}

		public void run()
		{
			Log.i(TAG, "BEGIN mConnectThread");
			setName("ConnectThread");

			// Always cancel discovery because it will slow down a connection
			mAdapter.cancelDiscovery();

			// Make a connection to the BluetoothSocket
			try
			{
				// Make a connection to the BluetoothSocket
				// This is a blocking call and will only return on a
				// successful connection or an exception
				mmSocket.connect();
			} catch (IOException e)
			{
				sendErrorMessage(R.string.bt_unable);
				// Close the socket
				try
				{
					mmSocket.close();
				} catch (IOException e2)
				{
					Log.e(TAG,
							"unable to close() socket during connection failure",
							e2);
				}
				// Start the service over to restart listening mode
				BtBartender.this.start();
				return;
			}
			// Reset the ConnectThread because we're done
			synchronized (BtBartender.this)
			{
				mConnectThread = null;
			}

			// Start the connected thread
			connected(mmSocket, mmDevice);
		}

		public void cancel()
		{
			try
			{
				mmSocket.close();
			} catch (IOException e)
			{
				Log.e(TAG, "close() of connect socket failed", e);
			}
		}
	}

	private class ConnectedThread extends Thread
	{
		private final BluetoothSocket mmSocket;
		private final InputStream mmInStream;
		private final OutputStream mmOutStream;

		public ConnectedThread(BluetoothSocket socket)
		{
			Log.d(TAG, "create ConnectedThread");
			mmSocket = socket;
			InputStream tmpIn = null;
			OutputStream tmpOut = null;

			// Get the BluetoothSocket input and output streams
			try
			{
				tmpIn = socket.getInputStream();
				tmpOut = socket.getOutputStream();
			} catch (IOException e)
			{
				Log.e(TAG, "temp sockets not created", e);
			}

			mmInStream = tmpIn;
			mmOutStream = tmpOut;
		}

		public void run()
		{
			Log.i(TAG, "BEGIN mConnectedThread");
			byte[] buffer = new byte[1024];
			int bytes;

			// Keep listening to the InputStream while connected
			while (mmInStream != null)
			{
				try
				{
					if (mmInStream.available() > 0)
					{
						// Read from the InputStream
						bytes = mmInStream.read(buffer);

						Log.d(TAG, new String(buffer));

						// Send the obtained bytes to the UI Activity
						for(BtMessageHandler handler : getHandlers())
						{
							handler.obtainMessage(
									BtMessageHandler.MessageType.READ, bytes, buffer)
									.sendToTarget();
						}
					} else
						Thread.sleep(100);

				} catch (IOException e)
				{
					Log.e(TAG, "disconnected", e);
					sendErrorMessage(R.string.bt_connection_lost);
					break;
				} catch (InterruptedException e)
				{
					Log.e(TAG, "Interrupted", e);
					sendErrorMessage(R.string.bt_connection_lost);
					break;
				}
			}
		}

		public void write(byte[] buffer)
		{
			try
			{
				Log.d(TAG, "write to mOut: " + new String(buffer, "UTF-8"));

				mmOutStream.write(buffer);
				mmOutStream.write(0x0d);
				mmOutStream.write(0x0a);

				// Share the sent message back to the UI Activity
				for(BtMessageHandler handler : getHandlers())
				{
					handler.obtainMessage(BtMessageHandler.MessageType.WRITE, -1,
							buffer).sendToTarget();
				}
			} catch (IOException e)
			{
				Log.e(TAG, "Exception during write", e);
			}
		}

		public void cancel()
		{
			try
			{
				mmSocket.close();
			} catch (IOException e)
			{
				Log.e(TAG, "close() of connect socket failed", e);
			}
		}
	}
}
