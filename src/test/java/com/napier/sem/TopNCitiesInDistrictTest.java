
package com.napier.sem;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class TopNCitiesInDistrictTest {

    @Test
    public void returnsEmptyListForInvalidDistrict() {
        App app = new App();

        ArrayList<City> result =
                app.getTopNCitiesInDistrict(null, 5);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void returnsEmptyListForInvalidN() {
        App app = new App();

        ArrayList<City> result =
                app.getTopNCitiesInDistrict("California", 0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
