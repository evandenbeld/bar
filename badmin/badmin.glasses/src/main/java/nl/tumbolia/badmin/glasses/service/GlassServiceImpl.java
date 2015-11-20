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
}
