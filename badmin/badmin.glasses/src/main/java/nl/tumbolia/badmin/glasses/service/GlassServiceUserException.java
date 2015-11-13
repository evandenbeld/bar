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

/**
 * Exception for error in the Glass service
 * 
 * @author erwin
 */
public class GlassServiceUserException extends RuntimeException
{
    private static final long serialVersionUID = 174496632989678113L;

    public enum GlassServiceUserError
    {
        EXISTS,
        NOT_FOUND,
    }

    public GlassServiceUserException(GlassServiceUserError error)
    {
        super(error.name());
    }

}
