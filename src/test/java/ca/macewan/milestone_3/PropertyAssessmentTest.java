package ca.macewan.milestone_3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PropertyAssessmentTest {

    @Test
    void isAssessmentClass() {
        String[] prop1 = {"1103530", "", "24249", "18 STREET NW",	"N", "2681", "EDMONTON ENERGY AND TECHNOLOGY PARK",
                "Ward 4", "946500",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop1Class = new PropertyAssessment(prop1);
        String[] prop2 = {"1103530", "", "24249", "18 STREET NW",	"N", "2681", "EDMONTON ENERGY AND TECHNOLOGY PARK",
                "Ward 4", "946500",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "", "","RESIDENTIAl"};
        PropertyAssessment prop2Class = new PropertyAssessment(prop2);
        //test exact typing
        assertEquals(true, prop1Class.isAssessmentClass("COMMERCIAL"));
        assertEquals(true, prop1Class.isAssessmentClass("RESIDENTIAL"));
        assertEquals(true, prop1Class.isAssessmentClass("FARMLAND"));

        //test different case typing
        assertEquals(true, prop1Class.isAssessmentClass("cOMMERCIAL"));
        assertEquals(true, prop1Class.isAssessmentClass("rESIDENTIAL"));
        assertEquals(true, prop1Class.isAssessmentClass("fARMLAND"));
        assertEquals(true, prop2Class.isAssessmentClass("rESIDENTIAL"));
        // test false
        assertEquals(false, prop1Class.isAssessmentClass("OMMERCIAL"));
        assertEquals(false, prop2Class.isAssessmentClass("752"));

        String[] prop3 = {"1103530", "", "", "18 STREET NW",	"Y", "", "EDMONTON ENERGY AND TECHNOLOGY PARK",
                "Ward 4", "",	"",	"",	"POINT (-113.36267819852408 53.70060958241598)",
                "", "", "", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop3Class = new PropertyAssessment(prop3);
        assertNotNull(prop3Class);



    }

    @Test
    void isInNeighbourhood() {
        String[] prop1 = {"1103530", "", "24249", "18 STREET NW",	"N", "2681", "WESTMOUNT",
                "Ward 4", "946500",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop1Class = new PropertyAssessment(prop1);
        String[] prop2 = {"1103530", "", "24249", "18 STREET NW",	"N", "2681", "WESTMOUNt",
                "Ward 4", "946500",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop2Class = new PropertyAssessment(prop2);
        assertEquals(true, prop1Class.isInNeighbourhood("WESTMOUNT"));
        assertEquals(true, prop1Class.isInNeighbourhood("wESTMOUNT"));
        assertEquals(true, prop1Class.isInNeighbourhood("wESTMOUNT"));
        assertEquals(false, prop1Class.isInNeighbourhood("downtown"));
        assertEquals(false, prop1Class.isInNeighbourhood(""));

    }

    @Test
    void isAssessedValueGreaterThan() {
        String[] prop1 = {"1103530", "", "24249", "18 STREET NW",	"N", "2681", "WESTMOUNT",
                "Ward 4", "1000",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop1Class = new PropertyAssessment(prop1);

        assertEquals(false, prop1Class.isAssessedValueGreaterThan(1001));
        assertEquals(false, prop1Class.isAssessedValueGreaterThan(1000));
        assertEquals(true, prop1Class.isAssessedValueGreaterThan(999));
    }

    @Test
    void assessedValue() {
        String[] prop1 = {"1103530", "", "24249", "18 STREET NW",	"N", "2681", "WESTMOUNT",
                "Ward 4", "1000",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop1Class = new PropertyAssessment(prop1);
        assertEquals(1000, prop1Class.assessedValue());
    }

    @Test
    void accountNumber() {
        String[] prop1 = {"1103530", "", "24249", "18 STREET NW",	"N", "2681", "WESTMOUNT",
                "Ward 4", "1000",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop1Class = new PropertyAssessment(prop1);
        assertEquals(1103530, prop1Class.accountNumber());
    }

    @Test
    public void testToString(){
        String[] prop1 = {"1103530", "", "24249", "18 STREET NW",	"N", "2681", "WESTMOUNT",
                "Ward 4", "10000",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop1Class = new PropertyAssessment(prop1);

        String addressAndValue = new String("AccountNumber = 1103530" + "\n" + "" + " " + 24249 + " " + "18 STREET NW" +
                "\nAssessedValue = " + "$10,000");

        String assessmentClassString = new String("\n[" + "COMMERCIAL" + " " + "52" + "%, "
                + "RESIDENTIAL" + " " + "42" + "%, "
                + "FARMLAND" + " " + "5" + "%]");

        String neighborhood = new String("\nWESTMOUNT" +" (" + "Ward 4" +")"
                + "\n(" + "53.70060958" + ", " + "-113.3626782" + ")");
        String expectedString = addressAndValue + assessmentClassString + neighborhood;

        assertEquals(expectedString, prop1Class.toString());
        int i = 0;

    }
    @Test
    void equals(){
        String[] prop1 = {"1103530", "", "24249", "18 STREET NW",	"N", "2681", "WESTMOUNT",
                "Ward 4", "946500",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop1Class = new PropertyAssessment(prop1);
        String[] prop2 = {"1103530", "", "24249", "18 STREET NW",	"N", "2681", "WESTMOUNT",
                "Ward 4", "946500",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop2Class = new PropertyAssessment(prop2);
        String[] prop3 = {"1103531", "", "24249", "18 STREET NW",	"N", "2681", "WESTMOUNT",
                "Ward 4", "946500",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop3Class = new PropertyAssessment(prop3);
        PropertyAssessment prop4Class = null;
        Integer x =0;

        assertEquals(true, prop1Class.equals(prop1Class));
        assertEquals(true, prop1Class.equals(prop2Class));
        assertEquals(false, prop3Class.equals(prop2Class));
        assertEquals(false, prop3Class.equals(prop4Class));
        assertEquals(false, prop3Class.equals(x));

    }

    @Test
    void testHashCode(){
        String[] prop1 = {"1103530", "", "24249", "18 STREET NW",	"N", "2681", "WESTMOUNT",
            "Ward 4", "946500",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
            "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop1Class = new PropertyAssessment(prop1);
        String[] prop2 = {"1103530", "", "24249", "18 STREET NW",	"N", "2681", "WESTMOUNT",
                "Ward 4", "946500",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop2Class = new PropertyAssessment(prop2);
        String[] prop3 = {"1103531", "", "24249", "18 STREET NW",	"N", "2681", "WESTMOUNT",
                "Ward 4", "946500",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop3Class = new PropertyAssessment(prop3);

        assertEquals(true, prop1Class.hashCode() == (prop2Class.hashCode()));
        assertEquals(false, prop3Class.hashCode() == (prop2Class.hashCode()));


    }

    @Test
    void getterTest(){
        String[] prop1 = {"1103530", "4a", "24249", "18 STREET NW",	"N", "2681", "WESTMOUNT",
                "Ward 4", "946500",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop1Class = new PropertyAssessment(prop1);
        String[] prop2 = {"1103530", "", "24249", "18 STREET NW",	"N", "2681", "WESTMOUNT",
                "Ward 4", "946500",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};
        PropertyAssessment prop2Class = new PropertyAssessment(prop2);
        String[] prop3 = {"1103531", "", "24249", "18 STREET NW",	"N", "2681", "WESTMOUNT",
                "Ward 4", "946500",	"53.70060958",	"-113.3626782",	"POINT (-113.36267819852408 53.70060958241598)",
                "52", "42", "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND"};

        assertEquals(1103530, prop1Class.getAccountNumber());
        assertEquals(1103530, prop1Class.accountNumber());
        assertEquals(946500, prop1Class.getAssessedValue());
        assertEquals(false, prop1Class.isHasGarage());

        String string1 = "4a" + " " + "24249" + " " + "18 STREET NW";
        assertEquals(string1, prop1Class.getAddress().toString());

        String string2 ="(" + "53.70060958" + ", " + "-113.3626782" + ")";
        assertEquals(string2, prop1Class.getLocation().toString());

        String string3 = "WESTMOUNT" +" (" + "Ward 4" +")";
        assertEquals(string3, prop1Class.getNeighbourhood().toString());

        String string4 ="[" + "COMMERCIAL" + " " + 52 + "%, "
                + "RESIDENTIAL" + " " + 42 + "%, "
                + "FARMLAND" + " " + 5 + "%]";
        assertEquals(string4, prop1Class.getAssessmentClass().toString());

    }
}