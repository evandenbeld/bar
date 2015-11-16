/*
 * 
 * 							   Bluetooth Bar Admin
 * 								BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.badmin.gui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * GUI for the BADMIN application.
 * 
 * @author erwin
 */
@SpringBootApplication
public class GuiApplication 
{
	
	//FIXME use parameters for ajax URL -> service discovery (or simply configuration, for now)
	//FIXME Better bower dependencies (to much added now) 
	//FIXME access control / login form
	//TODO Bower executed by maven
		
	public static void main(String[] args) 
	{
        SpringApplication.run(GuiApplication.class, args);
    }
}
