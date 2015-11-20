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

import com.google.common.base.Optional;

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
    
    private final Optional<Serializable> subject;

    public GlassServiceUserException(final GlassServiceUserError error)
    {
        super(error.name());
        this.subject = Optional.<Serializable>absent();
    }
    
    public GlassServiceUserException(final GlassServiceUserError error, final Serializable subject)
    {
        super(error.name());
        this.subject = Optional.of(subject);
    }

    public Optional<Serializable> getSubject()
    {
        return subject;
    }
}
