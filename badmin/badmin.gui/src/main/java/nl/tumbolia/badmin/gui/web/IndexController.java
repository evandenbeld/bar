/*
 * 
 * 							   Bluetooth Bar Admin
 * 								BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.badmin.gui.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO javadoc
 * 
 * @author erwin
 */
@Controller
public class IndexController
{
    //FIXME handle errors (see glass.html)
    
    //TODO Move glasses GUI bar.glasses?
    
    //FIXME new glass GUI functionality
    
    //FIXME delete glass GUI functionality
    
    @RequestMapping("/")
    public String greeting() 
    {
        return "index";
    }
}
