
package com.napier.sem;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class CountryIntegrationTest {
    @Test
    public void testGetCountries() {
        App app = new App();
        try {
            app.connect();
            ArrayList<Country> countries = app.getCountries();
            assertNotNull(countries);
            assertFalse(countries.isEmpty());
        } finally {
            app.disconnect();
        }
    }
}
