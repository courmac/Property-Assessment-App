package ca.macewan.milestone_3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssessmentClassTest {

    AssessmentClass assessmentClass1 = new AssessmentClass("52", "42",
                "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND");
    AssessmentClass assessmentClass2 = new AssessmentClass("75", "88",
                "9", "COMM", "RETAIL","FARLAND");
    AssessmentClass assessmentClass3 = new AssessmentClass("", "",
                "", "", "","");
    AssessmentClass assessmentClass4= new AssessmentClass("52", "42",
            "5", "COMMERCIAL", "RESIDENTIAL","FARMLAND");


    @Test
    void getAssessmentClassPercentOne() {
        assertEquals(52, assessmentClass1.getAssessmentClassPercentOne());
        assertEquals(75, assessmentClass2.getAssessmentClassPercentOne());
        assertEquals(0, assessmentClass3.getAssessmentClassPercentOne());
    }

    @Test
    void getAssessmentClassPercentTwo() {
        assertEquals(42, assessmentClass1.getAssessmentClassPercentTwo());
        assertEquals(88, assessmentClass2.getAssessmentClassPercentTwo());
        assertEquals(0, assessmentClass3.getAssessmentClassPercentTwo());
    }

    @Test
    void getAssessmentClassPercentThree() {
        assertEquals(5, assessmentClass1.getAssessmentClassPercentThree());
        assertEquals(9, assessmentClass2.getAssessmentClassPercentThree());
        assertEquals(0, assessmentClass3.getAssessmentClassPercentThree());
    }

    @Test
    void getAssessmentClassOne() {
        assertEquals("COMMERCIAL", assessmentClass1.getAssessmentClassOne());
        assertEquals("COMM", assessmentClass2.getAssessmentClassOne());
        assertEquals("", assessmentClass3.getAssessmentClassOne());
    }

    @Test
    void getAssessmentClassTwo() {
        assertEquals("RESIDENTIAL", assessmentClass1.getAssessmentClassTwo());
        assertEquals("RETAIL", assessmentClass2.getAssessmentClassTwo());
        assertEquals("", assessmentClass3.getAssessmentClassTwo());
    }

    @Test
    void getAssessmentClassThree() {
        assertEquals("FARMLAND", assessmentClass1.getAssessmentClassThree());
        assertEquals("FARLAND", assessmentClass2.getAssessmentClassThree());
        assertEquals("", assessmentClass3.getAssessmentClassThree());
    }

    @Test
    void testToString() {
        String string1 ="[" + "COMMERCIAL" + " " + 52 + "%, "
                + "RESIDENTIAL" + " " + 42 + "%, "
                + "FARMLAND" + " " + 5 + "%]";
        String string2 ="[" + "COMM" + " " + 75 + "%, "
                + "RETAIL" + " " + 88 + "%, "
                + "FARLAND" + " " + 9 + "%]";
        String string3 ="[" + "" + " " + 0 + "%, "
                + "" + " " + 0 + "%, "
                + "" + " " + 0 + "%]";

        assertEquals(string1, assessmentClass1.toString());
        assertEquals(string2, assessmentClass2.toString());
        assertEquals(string3, assessmentClass3.toString());
    }

    @Test
    void equalsTest(){
        assertEquals(assessmentClass1, assessmentClass4);
        assertEquals(false, assessmentClass1.equals(assessmentClass2));
        assertEquals(false, assessmentClass1.equals(null));
        assertEquals(true, assessmentClass1.equals(assessmentClass1));
        assertEquals(false, assessmentClass1.equals(10));

    }
    @Test
    void testHasCode(){
        assertEquals(true, assessmentClass1.hashCode() == assessmentClass4.hashCode());
    }
}