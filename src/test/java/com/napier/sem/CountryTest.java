package com.napier.sem;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class CountryTest
{
    @Test
    public void unitTest()
    {
        Country country = new Country("AUS", "Australia",
                "Oceania", "Australia and New Zealand", 18886000, "Canberra");

        assertEquals("AUS", country.getCountryCode());
        assertEquals("Australia", country.getName());
        assertEquals("Oceania", country.getContinent());
        assertEquals("Australia and New Zealand", country.getRegion());
        assertEquals(18886000, country.getPopulation());
        assertEquals("Canberra", country.getCapital());

    }

}