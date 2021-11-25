/**
 * Student's Name: Orest Dushkevich
 * Milestone #2
 * CMPT 305 LAB X02L Fall 2021
 * Instructor's Name: Indratmo Indratmo
 *
 * Purpose:
 * This Class is used to convert a PropertyAssessment object into a TableViewObject
 *
 *
 */package ca.macewan.milestone_3;

public class TableViewObject {
    private int account;
    private String address;
    private int assessedValue;
    private String assessmentClass;
    private String neighbourhood;
    private String location;

    TableViewObject (PropertyAssessment property){

        // converts the PropertyAssessment objects into a string
        account = property.getAccountNumber();
        address = property.getAddress().toString();
        assessedValue = property.getAssessedValue();
        assessmentClass = property.getAssessmentClass().toString();
        neighbourhood = property.getNeighbourhood().toString();
        location = property.getLocation().toString();

    }

    /**
     * getters for the tableView
     *
     */


    public int getAccount() {
        return account;
    }

    public String getAddress() {
        return address;
    }

    public int getAssessedValue() {
        return assessedValue;
    }

    public String getAssessmentClass() {
        return assessmentClass;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getLocation() {
        return location;
    }
}
