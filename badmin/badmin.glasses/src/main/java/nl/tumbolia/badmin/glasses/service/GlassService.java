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

import java.util.List;
import java.util.Optional;

import nl.tumbolia.badmin.glasses.domain.Glass;
import nl.tumbolia.badmin.glasses.domain.GlassIcon;

/**
 * Service for retrieving, validating and updating glasses.
 * 
 * @author erwin
 */
public interface GlassService
{
    List<Glass> getAllGlasses();
    
    Optional<GlassIcon> getGlassIcon(long id);
}
