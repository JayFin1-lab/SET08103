package com.napier.sem;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/*
Unit tests for the "PopCityNoCity.java"
 */
public class PopCityNoCityTest
{
    @Test
    public void percentageToHundred()
    {
        /*
        A row with 1000 total people, 250 of them in. The 0.001 is to account for round differences.
         */
        PopCityNoCity r = new PopCityNoCity("Test", 1000L, 250L);

        assertEquals(750L, r.getNotInCities());
        assertEquals(25.0, r.getCityPercentage(), 0.001);
        assertEquals(75.0, r.getNotinCityPercentage(), 0.001);

    }

    /*
    Edge case, the total of zero must not be divisible by zero.
    Case should return 0 for both of the percentages instead.
     */
    @Test
    public void TotalNotDivisibleByZero()
    {
        PopCityNoCity r = new PopCityNoCity("Empty", 0, 0);
        assertEquals(0.0, r.getCityPercentage(), 0.001);
        assertEquals(0.0, r.getNotinCityPercentage(), 0.001);

    }

}