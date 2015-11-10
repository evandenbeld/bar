/*
 * 
 * 							   Bluetooth Bar Admin
 * 								BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.bar.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * TODO javadoc
 * 
 * @author TODO erwin
 * @version TODO
 */
public class DistributionButton extends Button
{
	//TODO inject with guice?
	private Distributor distributor;
	
	public DistributionButton(Context context)
	{
		super(context);
	}

	public DistributionButton(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public DistributionButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public void setDistributor(Distributor distributor)
	{
		this.distributor = distributor;
	}

	
	@Override
	protected void onVisibilityChanged(View changedView, int visibility)
	{
		//FIXME overwrite gettext() instead?
		setText(distributor == null ? super.getText() : distributor.getLiquor());
		super.onVisibilityChanged(changedView, visibility);
	}

}
