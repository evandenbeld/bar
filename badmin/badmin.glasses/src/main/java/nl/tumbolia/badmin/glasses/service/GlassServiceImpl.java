/*
 * Bluetooth Bar Admin
 * -------------------
 * BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.badmin.glasses.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.tumbolia.badmin.glasses.domain.Glass;
import nl.tumbolia.badmin.glasses.repository.GlassIconRepository;
import nl.tumbolia.badmin.glasses.repository.GlassRepository;
import nl.tumbolia.badmin.glasses.service.GlassServiceUserException.GlassServiceUserError;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Main service implementation for managing glasses.
 * 
 * @author erwin
 */
@Service
public class GlassServiceImpl implements GlassService
{
    private static final String GLASS_NAME = "glass.name";

    @Autowired(required = true)
    private GlassRepository glassRepository;

    @Autowired(required = true)
    private GlassIconRepository glassIconRepository;

    @Override
    public List<Glass> getAllGlasses()
    {
        List<Glass> glasses = new ArrayList<Glass>();
        glassRepository.findAll().iterator().forEachRemaining(glasses::add);
        return glasses;
    }

    @Override
    public Optional<Glass> getGlass(final long id)
    {
        return Optional.ofNullable(glassRepository.findOne(id));
    }

    @Override
    public void addGlass(Glass glass)
    {
        basicGlassValidation(glass);        
        validateNewGlass(glass);
       
        glassRepository.save(glass);
    }   

    private static void basicGlassValidation(final Glass glass)
    {
        if(StringUtils.isEmpty(glass.getName()))
        {
            throw new GlassServiceUserException(GlassServiceUserError.MISSING_REQUIRED_ITEM, GLASS_NAME);
        }
        if(glass.getUnit() == null)
        {
            throw new GlassServiceUserException(GlassServiceUserError.MISSING_REQUIRED_ITEM, "glass.unit");
        }
        if(glass.getIcon() != null)
        {
            basicGlassIconValidation(glass);
        }
    }
    
    private static void basicGlassIconValidation(final Glass glass)
    {
        if(glass.isNew() != glass.getIcon().isNew())
        {
            throw new GlassServiceUserException(GlassServiceUserError.INVALID_ITEM, "icon.id");
        }
        if(glass.getIcon().getData() == null)
        {
            throw new GlassServiceUserException(GlassServiceUserError.MISSING_REQUIRED_ITEM, "icon.data");
        }       
    }
    
    private void validateNewGlass(Glass glass)
    {
        if(!glass.isNew())
        {
            throw new GlassServiceUserException(GlassServiceUserError.INVALID_ITEM, "glass.id");
        }
        if(glassRepository.findByName(glass.getName()) != null)
        {
            throw new GlassServiceUserException(GlassServiceUserError.DUPLICATE, GLASS_NAME);
        }
    }
    
    @Override
    public void updateGlass(Glass glass)
    {
        basicGlassValidation(glass);   
        validateExistingGlass(glass);

        glassRepository.save(glass);
    }
    
    private void validateExistingGlass(Glass glass)
    {
        if(glass.isNew())
        {
            throw new GlassServiceUserException(GlassServiceUserError.INVALID_ITEM, "glass.id");
        }
        if(!glassRepository.exists(glass.getId()))
        {
            throw new GlassServiceUserException(GlassServiceUserError.NOT_FOUND);
        }
        Glass glassWithSameName = glassRepository.findByName(glass.getName());
        if(glassWithSameName != null && !glassWithSameName.getId().equals(glass.getId()))
        {
            throw new GlassServiceUserException(GlassServiceUserError.DUPLICATE, GLASS_NAME);
        }
    }
}
