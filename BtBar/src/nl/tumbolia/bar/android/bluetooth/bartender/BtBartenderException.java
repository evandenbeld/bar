/*
 * 
 * 							    Bluetooth Bar App
 * 								BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.bar.android.bluetooth.bartender;

public class BtBartenderException extends RuntimeException
{
	public enum Error
	{
		NOT_CONNECTED,
		NO_POUR_MESSAGE_SET,
		MESSAGE_ENCODING_ERROR,
		UNKNOWN_VALVE_INDEX;
	}
	
	private final Error error;

	public BtBartenderException(Error error)
	{
		super(error.name());
		this.error = error;
	}

	public BtBartenderException(Error error, Throwable throwable)
	{
		super(error.name(), throwable);
		this.error = error;
	}

	public Error getError()
	{
		return error;
	}
	
}
