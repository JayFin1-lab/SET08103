
package com.napier.sem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TopNCitiesInRegionTest {

    @Test
    void returnsEmptyListForInvalidRegion() {
        App app = new App();

        assertTrue(app.getTopNCitiesInRegion(null, 5).isEmpty());
        assertTrue(app.getTopNCitiesInRegion("", 5).isEmpty());
        assertTrue(app.getTopNCitiesInRegion("Western Europe", 0).isEmpty());
        assertTrue(app.getTopNCitiesInRegion("Western Europe", -1).isEmpty());
    }
}
