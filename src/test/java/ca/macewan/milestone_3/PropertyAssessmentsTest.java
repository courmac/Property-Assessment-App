package ca.macewan.milestone_3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyAssessmentsTest {




    @Test
    void neighbourhoodStats() throws Exception {
        //PropertyAssessments propertyAssessmentsList = new PropertyAssessments();
        //String fileName= "Property_Assessment_Data_2021.csv";
        //propertyAssessmentsList.csvFileToPropertyAssessments(fileName);

        CsvPropertyAssessmentDAO propertyAssessmentsListDAO = new CsvPropertyAssessmentDAO();
        propertyAssessmentsListDAO.changeSource("Property_Assessment_Data_2021.csv");
        PropertyAssessments propertyAssessmentsList = propertyAssessmentsListDAO.getAll();
        int[] data = propertyAssessmentsList.neighbourhoodStats("granville");
        int[] expected= {1208, 3000, 33897000, 33894000, 436939, 423750};
        assertArrayEquals(expected, data);
        int[] data2 = propertyAssessmentsList.neighbourhoodStats("graille");
        assertNull(data2);



    }

    @Test
    void assessmentClassStats() throws Exception {
        //PropertyAssessments propertyAssessmentsList = new PropertyAssessments();
        //String fileName= "Property_Assessment_Data_2021.csv";
        //propertyAssessmentsList.csvFileToPropertyAssessments(fileName);

        CsvPropertyAssessmentDAO propertyAssessmentsListDAO = new CsvPropertyAssessmentDAO();
        propertyAssessmentsListDAO.changeSource("Property_Assessment_Data_2021.csv");
        PropertyAssessments propertyAssessmentsList = propertyAssessmentsListDAO.getAll();
        int[] data = propertyAssessmentsList.assessmentClassStats("residential");
        int[] expected= {384441, 0, 116910000, 116910000, 309290, 305500};
        assertArrayEquals(expected, data);
        int[] data2 = propertyAssessmentsList.assessmentClassStats("fefhb ra");
        assertNull(data2);
    }



    @Test
    void findAccount() throws Exception {
        //PropertyAssessments propertyAssessmentsList = new PropertyAssessments();
        //String fileName= "Property_Assessment_Data_2021.csv";
        //propertyAssessmentsList.csvFileToPropertyAssessments(fileName);

        CsvPropertyAssessmentDAO propertyAssessmentsListDAO = new CsvPropertyAssessmentDAO();
        propertyAssessmentsListDAO.changeSource("Property_Assessment_Data_2021.csv");
        PropertyAssessments propertyAssessmentsList = propertyAssessmentsListDAO.getAll();

        PropertyAssessment prop = propertyAssessmentsList.findAccount(1103530);
        assertEquals(1103530, prop.accountNumber());
        PropertyAssessment prop2 = propertyAssessmentsList.findAccount(787);
        assertNull( prop2);

    }


}