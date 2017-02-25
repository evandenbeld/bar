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

import java.io.Serializable;
import java.util.Optional;


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
        MISSING_REQUIRED_ITEM,
        INVALID_ITEM,
        DUPLICATE,
        NOT_FOUND,
    }
    
    private final GlassServiceUserError error;
    
	private final java.util.Optional<Serializable> subject;

    public GlassServiceUserException(final GlassServiceUserError error)
    {
        super(error.name());
        this.error = error;
		this.subject = Optional.empty();
    }
    
    public GlassServiceUserException(final GlassServiceUserError error, final Serializable subject)
    {
        super(error.name());
        this.error = error;
        this.subject = Optional.of(subject);
    }

    public GlassServiceUserError getError()
    {
        return error;
    }

    public Optional<Serializable> getSubject()
    {
        return subject;
    }
}
