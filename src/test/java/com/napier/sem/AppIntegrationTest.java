package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest {
    private static App app;
    private static Connection connection;

    @BeforeAll
    static void connectToDatabase() {
        app = new App();
        connection = app.connect();
        assertNotNull(connection, "Database connection should not be null");
    }

    @AfterAll
    static void disconnectFromDatabase() {
        if (app != null) {
            app.disconnect();
        }
    }

    @Test
    void countryQueryReturnsResults() {
        ArrayList<?> countries = Country.getCountries(connection, 10);

        assertNotNull(countries);
        assertFalse(countries.isEmpty());
        assertEquals(10, countries.size());
    }

    @Test
    void cityQueryReturnsResults() {
        ArrayList<?> cities = City.getCities(connection, 10);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());
        assertEquals(10, cities.size());
    }

}