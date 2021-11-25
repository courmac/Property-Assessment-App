package ca.macewan.milestone_3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CsvPropertyAssessmentDAOTest {

    @Test
    void getByAccountNumber() {
        CsvPropertyAssessmentDAO propertyAssessmentsListDAO = new CsvPropertyAssessmentDAO();
        propertyAssessmentsListDAO.changeSource("Property_Assessment_Data_2021.csv");
        PropertyAssessment prop = propertyAssessmentsListDAO.getByAccountNumber(1103530);

        assertEquals(1103530, prop.accountNumber());
        PropertyAssessment prop2 = propertyAssessmentsListDAO.getByAccountNumber(787);
        assertNull( prop2);
    }

    @Test
    void getByNeighbourhood() {
        CsvPropertyAssessmentDAO propertyAssessmentsListDAO = new CsvPropertyAssessmentDAO();
        propertyAssessmentsListDAO.changeSource("Property_Assessment_Data_2021.csv");
        PropertyAssessments propertyAssessmentsList = propertyAssessmentsListDAO.getByNeighbourhood("granville");
        int[] data = propertyAssessmentsList.propertyAssessmentStats();
        int[] expected= {1208, 3000, 33897000, 33894000, 436939, 423750};
        assertArrayEquals(expected, data);
        int[] data2 = propertyAssessmentsListDAO.getByNeighbourhood("graille").propertyAssessmentStats();
        assertNull(data2);
    }

    @Test
    void getByAssessmentClass() {
        CsvPropertyAssessmentDAO propertyAssessmentsListDAO = new CsvPropertyAssessmentDAO();
        propertyAssessmentsListDAO.changeSource("Property_Assessment_Data_2021.csv");
        PropertyAssessments propertyAssessmentsList = propertyAssessmentsListDAO.getByAssessmentClass("residential");
        int[] data = propertyAssessmentsList.propertyAssessmentStats();
        int[] expected= {384441, 0, 116910000, 116910000, 309290, 305500};
        assertArrayEquals(expected, data);
        int[] data2 = propertyAssessmentsListDAO.getByAssessmentClass("rez").propertyAssessmentStats();
        assertNull(data2);
    }

    @Test
    void getAll() {
        CsvPropertyAssessmentDAO propertyAssessmentsListDAO = new CsvPropertyAssessmentDAO();
        PropertyAssessments propertyAssessmentsList = propertyAssessmentsListDAO.getAll();
        assertEquals(410953, propertyAssessmentsList.getPropertyList().size());
    }

    @Test
    void changeSource() {
        CsvPropertyAssessmentDAO propertyAssessmentsListDAO = new CsvPropertyAssessmentDAO();
        propertyAssessmentsListDAO.setGetLessValue(9999);
        PropertyAssessments propertyAssessmentsList = propertyAssessmentsListDAO.getLess();
        assertEquals(9999, propertyAssessmentsList.getPropertyList().size());
    }
}