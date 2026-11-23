package com.napier.sem;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    @Test
    public void unitTest()
    {
        City testcity = new City(1890, "Shanghai", "CHN", "Shanghai", 9696300);

        assertEquals(1890, testcity.getId());
        assertEquals("Shanghai", testcity.getName());
        assertEquals("CHN", testcity.getCountryCode());
        assertEquals("Shanghai", testcity.getDistrict());
        assertEquals(9696300, testcity.getPopulation());
    }
}