package ca.macewan.milestone_3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest {
    Location location1 = new Location("54.75550958",
            "-113.3336782",
            "POINT (-113.36267819852408 53.70060958241598)");
    Location location2 = new Location("53.70060958",
            "-113.3626782",
            "POINT (-113.36267619852408 54.70060958241598)");
    Location location3 = new Location("54.75550958e",
            "-113.333678r2",
            "POINT (-113.36267819852408 53.70060958241598)");
    Location location4 = new Location("",
            "",
            "POINT (-113.36267819852408 53.70060958241598)");
    Location location5 = new Location("54.75550958",
            "-113.3336782",
            "POINT (-113.36267819852408 53.70060958241598)");

    @Test
    void getPointLocation() {
        assertEquals("POINT (-113.36267819852408 53.70060958241598)",
                location1.getPointLocation());
        assertEquals("POINT (-113.36267619852408 54.70060958241598)",
                location2.getPointLocation());
    }

    @Test
    void getLatitude() {
        assertEquals(54.75550958, location1.getLatitude());
        assertEquals(53.70060958, location2.getLatitude());
        assertEquals(0, location3.getLatitude());
        assertEquals(0, location4.getLatitude());
    }

    @Test
    void getLongitude() {
        assertEquals(-113.3336782, location1.getLongitude());
        assertEquals(-113.3626782, location2.getLongitude());
        assertEquals(0, location3.getLongitude());
        assertEquals(0, location4.getLongitude());
    }

    @Test
    void testToString() {
        String string1 ="(" + "54.75550958" + ", " + "-113.3336782" + ")";
        String string2 ="(" + "53.70060958" + ", " + "-113.3626782" + ")";
        assertEquals(string1, location1.toString());
        assertEquals(string2, location2.toString());
    }
    @Test
    void equalsTest(){
        assertEquals(location1, location5);
        assertEquals(false, location1.equals(location2));
        assertEquals(false, location1.equals(null));
        assertEquals(true, location1.equals(location1));
        assertEquals(false, location1.equals(10));

    }
    @Test
    void testHasCode(){
        assertEquals(true, location1.hashCode() == location5.hashCode());
    }
}