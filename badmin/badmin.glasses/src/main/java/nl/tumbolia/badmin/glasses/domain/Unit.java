/*
 * Bluetooth Bar Admin
 * -------------------
 * BEER-WARE LICENSE
 * 
 * <erwin@tumbolia.nl> wrote this software. As long as you retain this notice 
 * you can do whatever you want with this stuff. If we meet some day, and you 
 * think this stuff is worth it, you can buy me a beer in return
 */
package nl.tumbolia.badmin.glasses.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Unit for glasses
 * TODO use a libraby for (SI)-Units
 * @author erwin
 */
public enum Unit implements Serializable
{
    LITER("Liter", "l"),
    MILLI_LITER("Milliliter", "ml"),
    SQUARE_METER("M", "m3"),
    CUBIC_CENTIMETRE("Cubic centimetre", "cm3");

    private String name;
    private String symbol;

    private Unit(String name, String symbol)
    {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName()
    {
        return name;
    }

    @JsonValue
    public String getSymbol()
    {
        return symbol;
    }
}
