/**
 * Student's Name: Orest Dushkevich and Courtney McNeilly
 * Milestone #2
 * CMPT 305 LAB X02L Fall 2021
 * Instructor's Name: Indratmo Indratmo
 *
 * Purpose:
 * This Interface holds the default methods for the PropertyAssessmentDAO that must be implemented
 *
 */

package ca.macewan.milestone_3;

public interface PropertyAssessmentDAO {
    String source = "";
    PropertyAssessment getByAccountNumber( int accountNumber);

    PropertyAssessments getByNeighbourhood(String neighbourhood);

    PropertyAssessments getByAssessmentClass(String assessmentClass);

    PropertyAssessments getAll();

    PropertyAssessments getLess();

    void changeSource(String source);

}
