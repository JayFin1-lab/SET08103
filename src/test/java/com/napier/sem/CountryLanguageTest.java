package com.napier.sem;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CountryLanguageTest {
    @Test
    public void unitTest()
    {
        CountryLanguage testLanguage = new CountryLanguage("English",347077867, 5.71);

        assertEquals("English", testLanguage.getLanguage());
        assertEquals(347077867, testLanguage.getSpeakers());
        assertEquals(5.71, testLanguage.getWorldPercent());
    }
}
