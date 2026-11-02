package com.napier.sem;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    @Test
    public void unitTest()
    {
        City testcity = new City(1, "test cityname", "test countrycode", "test district", 10);

        assertEquals(1, testcity.getId());
        assertEquals("test cityname", testcity.getName());
        assertEquals("test countrycode", testcity.getCountryCode());
        assertEquals("test district", testcity.getDistrict());
        assertEquals(10, testcity.getPopulation());
    }
}