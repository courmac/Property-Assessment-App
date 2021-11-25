package ca.macewan.milestone_3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableViewObjectTest {
    String[] prop1 = {"1103530", "", "24249", "18 STREET NW",	"N", "2681", "EDMONTON ENERGY AND TECHNOLOGY PARK",
            "Ward 4", "946500",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
            "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
    PropertyAssessment property1 = new PropertyAssessment(prop1);

    TableViewObject obj1 = new TableViewObject(property1);

    @Test
    void getAccount() {
        assertEquals(1103530, obj1.getAccount());
    }

    @Test
    void getAddress() {
        assertEquals(" 24249 18 STREET NW", obj1.getAddress());
    }

    @Test
    void getAssessedValue() {
        assertEquals(946500, obj1.getAssessedValue());
    }

    @Test
    void getAssessmentClass() {
        assertEquals("[COMMERCIAL 52%, RESIDENTIAL 42%, FARMLAND 5%]", obj1.getAssessmentClass());
    }

    @Test
    void getNeighbourhood() {
        assertEquals("EDMONTON ENERGY AND TECHNOLOGY PARK (Ward 4)", obj1.getNeighbourhood());
    }

    @Test
    void getLocation() {
        assertEquals("(53.70060958, -113.3626782)", obj1.getLocation());
    }
}