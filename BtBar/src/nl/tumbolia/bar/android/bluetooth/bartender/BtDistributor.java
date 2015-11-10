/*
 * 
 * 							   Bluetooth Bar App
 * 								BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.bar.android.bluetooth.bartender;

import java.io.UnsupportedEncodingException;

import nl.tumbolia.bar.android.Distributor;
import android.util.Log;

public class BtDistributor implements Distributor
{
	private static final String TAG = "BtDistributor";	
	
	private static final String VALVE_HARDWARE_MESSAGE = "v %s %s";		
	
	private int id;
	private String liquor;
	private int timeout;
	private int hardwareId;
	
	private BtBartender bartender;
	
	public BtDistributor(final BtBartender bartender, final int id)
	{
		this.bartender = bartender;
		
		this.id = id;
		liquor = null;
		timeout = 100;
		hardwareId = 1;
	}

	public BtDistributor(final BtBartender bartender, final int id, final String liquor, final int timeout, final int hardwareId)
	{	
		this(bartender, id);
		
		this.liquor = liquor;
		this.timeout = timeout;
		this.hardwareId = hardwareId;
	}

	public int getId()
	{
		return id;
	}

	@Override
	public String getLiquor()
	{
		return liquor;
	}

	public void setLiquor(final String liquor)
	{
		this.liquor = liquor;
	}

	public int getTimeout()
	{
		return timeout;
	}

	public void setTimeout(final int timeout)
	{
		this.timeout = timeout;
	}

	public int getHardwareId()
	{
		return hardwareId;
	}

	public void setHardwareId(final int hardwareId)
	{
		this.hardwareId = hardwareId;
	}			

	@Override
	public void pour()
	{
		bartender.distribute(getHardwareMessage());				
	}
	
	private byte[] getHardwareMessage()
	{
		try
		{
			return String.format(VALVE_HARDWARE_MESSAGE, getHardwareId(), getTimeout()).getBytes("UTF-8");
		} 
		catch (UnsupportedEncodingException e)
		{
			Log.e(TAG, e.getMessage(), e);
			throw new BtBartenderException(BtBartenderException.Error.MESSAGE_ENCODING_ERROR, e);
		}
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BtDistributor other = (BtDistributor) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
