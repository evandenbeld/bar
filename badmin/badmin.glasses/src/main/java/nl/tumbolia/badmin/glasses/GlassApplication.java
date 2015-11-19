/*
 * 
 * Bluetooth Bar Admin
 * BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.badmin.glasses;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import nl.tumbolia.badmin.glasses.domain.Glass;
import nl.tumbolia.badmin.glasses.domain.GlassIcon;
import nl.tumbolia.badmin.glasses.repository.GlassRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring MVC Glasses Spring boot micro-service.
 * 
 * @author erwin
 */
@SpringBootApplication
public class GlassApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(GlassApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(GlassRepository glassRepository)
    {
        // TODO real database
        return args -> {
            // save a couple of glasses
            glassRepository.save(createGlass(1, "Flute"));
            glassRepository.save(createGlass(2, "Champagne glass"));
            glassRepository.save(createGlass(3, "Beer glass"));

        };
    }

    private Glass createGlass(final int id, final String name)
            throws IOException
    {
        Glass glass = new Glass(name, 200);
        GlassIcon icon = new GlassIcon(createIconData(id + ".png"));
        glass.setIcon(icon);
        return glass;
    }

    private byte[] createIconData(final String image) throws IOException
    {
        // Retrieve image from the classpath.
        InputStream is = this.getClass()
                .getResourceAsStream("/public/" + image);
        BufferedImage img = ImageIO.read(is);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();

        ImageIO.write(img, "png", bao);

        return bao.toByteArray();
    }
}
