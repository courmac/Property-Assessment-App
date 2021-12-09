/**
 * Student's Name: Orest Dushkevich and Courtney McNeilly
 * Milestone #3
 * CMPT 305 LAB X02L Fall 2021
 * Instructor's Name: Indratmo Indratmo
 *
 * Purpose:
 * This Class holds an assessmentClass object and the appropriate getters and toSting methods
 *
 */
package ca.macewan.milestone_3;

import java.util.Objects;

public class AssessmentClass {
    private int assessmentClassPercentOne, assessmentClassPercentTwo, assessmentClassPercentThree;
    private String  assessmentClassOne, assessmentClassTwo, assessmentClassThree;

    public AssessmentClass(String assessmentClassPercentOne, String assessmentClassPercentTwo, String assessmentClassPercentThree, String assessmentClassOne, String assessmentClassTwo,String assessmentClassThree){

        if (assessmentClassPercentOne.equals("")) {
            this.assessmentClassPercentOne = 0;
        } else {
            this.assessmentClassPercentOne = Integer.parseInt(assessmentClassPercentOne);
        }

        if (assessmentClassPercentTwo.equals("")) {
            this.assessmentClassPercentTwo = 0;
        } else {
            this.assessmentClassPercentTwo = Integer.parseInt(assessmentClassPercentTwo);
        }

        if (assessmentClassPercentThree.equals("")) {
            this.assessmentClassPercentThree = 0;
        } else {
            this.assessmentClassPercentThree = Integer.parseInt(assessmentClassPercentThree);
        }

        this.assessmentClassOne = assessmentClassOne;
        this.assessmentClassTwo = assessmentClassTwo;
        this.assessmentClassThree = assessmentClassThree;

    }

    public int getAssessmentClassPercentOne() {
        return assessmentClassPercentOne;
    }

    public int getAssessmentClassPercentTwo() {
        return assessmentClassPercentTwo;
    }

    public int getAssessmentClassPercentThree() {
        return assessmentClassPercentThree;
    }

    public String getAssessmentClassOne() {
        return assessmentClassOne;
    }

    public String getAssessmentClassTwo() {
        return assessmentClassTwo;
    }

    public String getAssessmentClassThree() {
        return assessmentClassThree;
    }

    @Override
    public String toString() {
        return "[" + assessmentClassOne + " " + assessmentClassPercentOne + "%, "
                + assessmentClassTwo + " " + assessmentClassPercentTwo + "%, "
                + assessmentClassThree + " " + assessmentClassPercentThree + "%]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssessmentClass that = (AssessmentClass) o;
        return assessmentClassPercentOne == that.assessmentClassPercentOne && assessmentClassPercentTwo == that.assessmentClassPercentTwo && assessmentClassPercentThree == that.assessmentClassPercentThree && assessmentClassOne.equals(that.assessmentClassOne) && assessmentClassTwo.equals(that.assessmentClassTwo) && assessmentClassThree.equals(that.assessmentClassThree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessmentClassPercentOne, assessmentClassPercentTwo, assessmentClassPercentThree, assessmentClassOne, assessmentClassTwo, assessmentClassThree);
    }
}
