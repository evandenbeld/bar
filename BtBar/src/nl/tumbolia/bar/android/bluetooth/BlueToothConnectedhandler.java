/*
 * 
 * 							   Bluetooth Bar Admin
 * 								BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.bar.android.bluetooth;

import nl.tumbolia.bar.android.bluetooth.bartender.BtState;
import android.os.Message;

/**
 * TODO javadoc
 * 
 * @author TODO erwin
 * @version TODO
 */
public abstract class BlueToothConnectedhandler extends BtMessageHandler
{
	@Override
	public void handleMessage(Message msg)
	{
		BtMessageHandler.MessageType messageType = BtMessageHandler.MessageType
				.values()[msg.what];
		if(messageType == BtMessageHandler.MessageType.STATE)
		{
			connectionStateChanged(((BtState) msg.obj) == BtState.CONNECTED);
		}
	}
	
	protected abstract void connectionStateChanged(boolean connected);	
}
