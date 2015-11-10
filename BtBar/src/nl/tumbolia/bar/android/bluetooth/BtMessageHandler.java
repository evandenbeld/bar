/*
 * 
 * 							   Bluetooth Bar App
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
package nl.tumbolia.bar.android.bluetooth;

import android.os.Handler;
import android.os.Message;

public class BtMessageHandler extends Handler
{
	public enum MessageType
	{
		STATE, READ, WRITE, DEVICE, NOTIFY;
	}

	public Message obtainMessage(MessageType message, int count, Object obj)
	{
		return obtainMessage(message.ordinal(), count, -1, obj);

	}

	public MessageType getMessageType(int ordinal)
	{
		return MessageType.values()[ordinal];
	}

}
