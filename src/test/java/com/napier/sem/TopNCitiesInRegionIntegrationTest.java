
package com.napier.sem;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TopNCitiesInRegionIntegrationTest {

    @Test
    void returnsTopFiveCitiesInRegion() {
        App app = new App();
        app.connect();

        try {
            ArrayList<City> cities =
                    app.getTopNCitiesInRegion("Western Europe", 5);

            assertEquals(5, cities.size(),
                    "Expected five cities from Western Europe");

            for (int i = 1; i < cities.size(); i++) {
                assertTrue(
                        cities.get(i - 1).getPopulation()
                                >= cities.get(i).getPopulation(),
                        "Cities should be sorted by population descending"
                );
            }
        } finally {
            app.disconnect();
        }
    }
}
