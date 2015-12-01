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
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping(value="/glasses")
public class GlassController
{
    private static final Logger LOG = LoggerFactory.getLogger(GlassController.class);

    @Autowired
    private GlassService glassService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Glass> getAllGlasses()
    {
        return glassService.getAllGlasses();
    }

    @RequestMapping(value = "{glassId}/icon", method = RequestMethod.GET, produces = "image/png")
    @ResponseBody
    public byte[] getGlassIcon(@PathVariable final long glassId)
    {        
        GlassIcon icon = getGlass(glassId).getIcon();
        if (icon == null)
        {
            LOG.debug("Icon not found for glass with ID " + glassId);
            throw new ResourceNotFoundException();
        }
        return icon.getData();
    }

    private Glass getGlass(final long glassId)
    {
        Optional<Glass> glass = glassService.getGlass(glassId);
        if (!glass.isPresent())
        {
            LOG.debug("Glass not found with ID " + glassId);
            throw new ResourceNotFoundException();
        }
        return glass.get();
    }   
    
    @RequestMapping(method = RequestMethod.POST)
    public void createGlass(@RequestBody final Glass glass)
    {
       glassService.addGlass(glass);
    }

    //FIXME exception handling -> convert user errror 
    @RequestMapping(method = RequestMethod.PUT)
    public void updateGlass(@RequestBody final Glass glass)
    {
        glassService.updateGlass(glass);
    }
}
