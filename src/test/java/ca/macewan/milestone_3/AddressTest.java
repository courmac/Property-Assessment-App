package ca.macewan.milestone_3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AddressTest {
    Address address1 = new Address("4a", "12345","fake st");
    Address address2 = new Address("85we", "", "real st");
    Address address3 = new Address("4a", "12345","fake st");


    @Test
    void getSuite() {
        assertEquals("4a", address1.getSuite());
        assertEquals("85we", address2.getSuite());
    }

    @Test
    void getHouseNumber() {
        assertEquals(12345, address1.getHouseNumber());
        assertEquals(0, address2.getHouseNumber());
    }

    @Test
    void getsStreetName() {
        assertEquals("fake st", address1.getStreetName());
        assertEquals("real st", address2.getStreetName());
    }

    @Test
    void testToString() {
        String string1 = "4a" + " " + "12345" + " " + "fake st";
        String string2 = "85we" + " " + "0" + " " + "real st";
        assertEquals(string1, address1.toString());
        assertEquals(string2, address2.toString());
        assertNotEquals(string1, address2.toString());
    }
    @Test
    void equalsTest(){
        assertEquals(address1, address3);
        assertEquals(false, address1.equals(address2));
        assertEquals(false, address1.equals(null));
        assertEquals(true, address1.equals(address1));
        assertEquals(false, address1.equals(new Integer(10)));

    }
    @Test
    void testHasCode(){
        assertEquals(true, address1.hashCode() == address3.hashCode());
    }
}