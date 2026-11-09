package com.napier.sem;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CapitalCityTest {
    @Test
    public void unitTest()
    {
        CapitalCity testCapitals = new CapitalCity("Edinburgh", "Scotland", 450180);

        assertEquals("Edinburgh", testCapitals.getName());
        assertEquals("Scotland", testCapitals.getCountry());
        assertEquals(450180, testCapitals.getPopulation());
    }
}
