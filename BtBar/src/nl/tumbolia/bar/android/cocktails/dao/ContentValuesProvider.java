/*
 * 
 * 							   Bluetooth Bar App
 * 								BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.bar.android.cocktails.dao;

import android.content.ContentValues;

/**
 * TODO javadoc
 * 
 * @author TODO erwin
 * @version TODO
 */
public interface ContentValuesProvider
{
	ContentValues toContentValues();
	
	void setId(long id);
}
