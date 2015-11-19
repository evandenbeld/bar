/*
 * Bluetooth Bar Admin
 * -------------------
 * BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.badmin.glasses.repository;

import nl.tumbolia.badmin.glasses.domain.GlassIcon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring JPA repository for glass icons
 * 
 * @author erwin
 */
@Repository
public interface GlassIconRepository extends JpaRepository<GlassIcon, Long>
{

}
