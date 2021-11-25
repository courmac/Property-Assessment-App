package ca.macewan.milestone_3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NeighbourhoodTest {

    Neighbourhood neighbourhood1 = new Neighbourhood("2681",
            "WESTMOUNT",
            "Ward 4");
    Neighbourhood neighbourhood2 = new Neighbourhood("2781",
            "DOWN",
            "Ward 7");
    Neighbourhood neighbourhood3 = new Neighbourhood("",
            "",
            "");
    Neighbourhood neighbourhood4 = new Neighbourhood("2681",
            "WESTMOUNT",
            "Ward 4");

    @Test
    void getNeighbourhood() {
        assertEquals("WESTMOUNT",neighbourhood1.getNeighbourhood());
        assertEquals("DOWN",neighbourhood2.getNeighbourhood());
        assertEquals("",neighbourhood3.getNeighbourhood());
    }

    @Test
    void getNeighbourhoodId() {
        assertEquals(2681,neighbourhood1.getNeighbourhoodId());
        assertEquals(2781,neighbourhood2.getNeighbourhoodId());
        assertEquals(0,neighbourhood3.getNeighbourhoodId());
    }

    @Test
    void getWard() {
        assertEquals("Ward 4",neighbourhood1.getWard());
        assertEquals("Ward 7",neighbourhood2.getWard());
        assertEquals("",neighbourhood3.getWard());
    }

    @Test
    void testToString() {
        String string1 = "WESTMOUNT" +" (" + "Ward 4" +")";
        String string2 = "DOWN" +" (" + "Ward 7" +")";
        String string3 = "" +" (" + "" +")";
        assertEquals(string1, neighbourhood1.toString());
        assertEquals(string2, neighbourhood2.toString());
        assertEquals(string3, neighbourhood3.toString());

    }

    @Test
    void equalsTest(){
        assertEquals(neighbourhood1, neighbourhood4);
        assertEquals(false, neighbourhood1.equals(neighbourhood2));
        assertEquals(false, neighbourhood1.equals(null));
        assertEquals(true, neighbourhood1.equals(neighbourhood1));
        assertEquals(false, neighbourhood1.equals(10));

    }
    @Test
    void testHasCode(){
        assertEquals(true, neighbourhood1.hashCode() == neighbourhood4.hashCode());
    }
}