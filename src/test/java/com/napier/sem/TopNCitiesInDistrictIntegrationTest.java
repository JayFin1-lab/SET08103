
package com.napier.sem;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class TopNCitiesInDistrictIntegrationTest {
    @Test
    public void returnsTopFiveCitiesInDistrict() {
        App app = new App();
        try {
            app.connect();
            ArrayList<City> cities =
                    app.getTopNCitiesInDistrict("California", 5);
            assertNotNull(cities);
            assertEquals(5, cities.size());
        } finally {
            app.disconnect();
        }
    }
}
