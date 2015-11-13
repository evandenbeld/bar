/*
 * 
 * Bluetooth Bar Admin
 * BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.badmin.glasses.web;

import java.util.List;
import java.util.Optional;

import nl.tumbolia.badmin.glasses.domain.Glass;
import nl.tumbolia.badmin.glasses.domain.GlassIcon;
import nl.tumbolia.badmin.glasses.service.GlassService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC Glasses controller
 * 
 * @author erwin
 */
@RestController
public class GlassController 
{
	private static final Logger LOG = LoggerFactory.getLogger(GlassController.class); 
	
	@Autowired
	private GlassService glassService;

	@RequestMapping("/glasses")
	public List<Glass> getAllGlasses() 
	{
		return glassService.getAllGlasses();
	}

	@RequestMapping(value = "/glassicon/{id}", method = RequestMethod.GET, produces = "image/png")
	@ResponseBody 
	public byte[] getGlassIcon(@PathVariable final long id) 
	{
		Optional<GlassIcon> icon = glassService.getGlassIcon(id);
		if(!icon.isPresent())
		{
			LOG.debug("Glass icon not found with ID " + id);
			throw new ResourceNotFoundException();
		}
		return icon.get().getData();		
	}
}
