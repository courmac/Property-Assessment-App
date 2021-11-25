package ca.macewan.milestone_3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ApiPropertyAssessmentDAOTest {

    @Test
    void getByAccountNumber() {

        ApiPropertyAssessmentDAO propertyAssessmentsListDAO = new ApiPropertyAssessmentDAO();

        PropertyAssessment prop = propertyAssessmentsListDAO.getByAccountNumber(1103530);

        assertEquals(1103530, prop.accountNumber());
        PropertyAssessment prop2 = propertyAssessmentsListDAO.getByAccountNumber(787);
        assertNull( prop2);

    }

    @Test
    void getByNeighbourhood() {
        ApiPropertyAssessmentDAO propertyAssessmentsListDAO = new ApiPropertyAssessmentDAO();

        PropertyAssessments propertyAssessmentsList = propertyAssessmentsListDAO.getByNeighbourhood("granville");
        int[] data = propertyAssessmentsList.propertyAssessmentStats();
        int[] expected= {1208, 3000, 33897000, 33894000, 424500, 423750};
        assertEquals(expected[0], data[0]);
        int[] data2 = propertyAssessmentsListDAO.getByNeighbourhood("graille").propertyAssessmentStats();
        assertNull(data2);
    }

    @Test
    void getByAssessmentClass() {
        ApiPropertyAssessmentDAO propertyAssessmentsListDAO = new ApiPropertyAssessmentDAO();

        PropertyAssessments propertyAssessmentsList = propertyAssessmentsListDAO.getByAssessmentClass("residential");
        int[] data = propertyAssessmentsList.propertyAssessmentStats();
        int[] expected= {384672, 0, 116910000, 116910000, 309783, 306000};
        assertEquals(expected[0], data[0]);
        int[] data2 = propertyAssessmentsListDAO.getByAssessmentClass("rez").propertyAssessmentStats();
        assertNull(data2);
    }

    @Test
    void getAll() {

        // this test gets the size value from https://data.edmonton.ca/widgets/q7d6-ambg?mobile_redirect=true
        ApiPropertyAssessmentDAO propertyAssessmentsListDAO = new ApiPropertyAssessmentDAO();
        PropertyAssessments propertyAssessmentsList = propertyAssessmentsListDAO.getAll();
        assertEquals(411167, propertyAssessmentsList.getPropertyList().size());

    }

    @Test
    void changeSource() {
        ApiPropertyAssessmentDAO propertyAssessmentsListDAO = new ApiPropertyAssessmentDAO();
        assertEquals("https://data.edmonton.ca/resource/q7d6-ambg.csv",propertyAssessmentsListDAO.getSource());
        propertyAssessmentsListDAO.changeSource("new Source");
        assertEquals("new Source",propertyAssessmentsListDAO.getSource());
    }

    @Test
    void getLess() {
        ApiPropertyAssessmentDAO propertyAssessmentsListDAO = new ApiPropertyAssessmentDAO();
        propertyAssessmentsListDAO.setOffsetSize(1000);
        PropertyAssessments propertyAssessmentsList = propertyAssessmentsListDAO.getLess();
        assertEquals(1000, propertyAssessmentsList.getPropertyList().size());


        propertyAssessmentsListDAO = new ApiPropertyAssessmentDAO();
        propertyAssessmentsListDAO.setOffsetSize(15000);
        propertyAssessmentsList = propertyAssessmentsListDAO.getLess();
        assertEquals(15000, propertyAssessmentsList.getPropertyList().size());
    }
}