/**
 * Student's Name: Orest Dushkevich
 * Milestone #2
 * CMPT 305 LAB X02L Fall 2021
 * Instructor's Name: Indratmo Indratmo
 *
 * Purpose:
 * This Class holds a single property assessment and all associated data, it also contains methods for previous
 * milestones and labs
 *
 *
 */
package ca.macewan.milestone_3;

import java.util.Objects;

import static java.lang.String.format;

public class PropertyAssessment {

    //assumption that no property will ever be worth over $2,147,483,647
    private int accountNumber;
    private int assessedValue;
    private boolean hasGarage;
    private Address address;
    private Location location;
    private Neighbourhood neighbourhood;
    private AssessmentClass assessmentClass;

    /**
     * This constructor creates a PropertyAssessment Object from a string[] where the data follows this order:
     * [Account Number,	Suite,	House Number,	Street Name	Garage,	Neighbourhood ID,	Neighbourhood,	Ward,	\
     * Assessed Value,	Latitude,	Longitude,	Point Location,	Assessment Class % 1,	Assessment Class % 2,	\
     * Assessment Class % 3,	Assessment Class 1,	Assessment Class 2,	Assessment Class 3]
     *
     * the constructor parses the data and makes any necessary conversions
     * @param dataRow list of Strings that contains a row of data
     */
    public PropertyAssessment(String[] dataRow) {

        accountNumber = Integer.parseInt( dataRow[0]);
        address = new Address(dataRow[1],dataRow[2],dataRow[3]);

        // will default to false if string is empty
        hasGarage = dataRow[4].equals("Y");
        neighbourhood = new Neighbourhood(dataRow[5],dataRow[6], dataRow[7]);

        if (dataRow[8].equals("")) {
            assessedValue = 0;
        } else {
            assessedValue = Integer.parseInt(dataRow[8]);
        }
        location = new Location(dataRow[9],dataRow[10],dataRow[11]);
        assessmentClass = new AssessmentClass(dataRow[12], dataRow[13], dataRow[14], dataRow[15], dataRow[16],dataRow[17]);

    }

    /**
     * checks to see if the PropertyAssessment object is contains assessmentClassName as one of its assessment classes
     * the program does a non case-sensitive search
     * @param assessmentClassName string of the assessment class to compare against
     * @return true if there is a match
     */
    public boolean isAssessmentClass(String assessmentClassName){
        String className = assessmentClassName.toUpperCase();
        return assessmentClass.getAssessmentClassOne().toUpperCase().equals(className)
                || assessmentClass.getAssessmentClassTwo().toUpperCase().equals(className)
                || assessmentClass.getAssessmentClassThree().toUpperCase().equals(className);

    }

    /**
     * checks to see if the PropertyAssessment object's neighbourhood matches with neighbourhoodName
     * the program does a non case-sensitive search
     * @param neighbourhoodName string of the neighbourhood name to compare against
     * @return true if there is a match
     */
    public boolean isInNeighbourhood(String neighbourhoodName){
        String searchName = neighbourhoodName.toUpperCase();
        return neighbourhood.getNeighbourhood().toUpperCase().equals(searchName);

    }

    /**
     * checks to see if this.assessedValue > assessedValue
     * returns true if it's greater, false otherwise
     * @param assessedValue string of the assessment class to compare against
     * @return true if there is a match
     */
    public boolean isAssessedValueGreaterThan(int assessedValue){

        return this.assessedValue > assessedValue;

    }

    /**
     * returns the assessedValue of a PropertyAssessment
     * @return  int op the propertyAssessment
     */
    public int assessedValue(){

        return assessedValue;
    }


    /**
     * returns the accountNumber
     * @return account number as an in int
     */
    public int accountNumber(){
        return accountNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getAssessedValue() {
        return assessedValue;
    }

    public boolean isHasGarage() {
        return hasGarage;
    }

    public Address getAddress() {
        return address;
    }

    public Location getLocation() {
        return location;
    }

    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }

    public AssessmentClass getAssessmentClass() {
        return assessmentClass;
    }

    @Override
    public String toString(){
        String addressAndValue ="AccountNumber = " + accountNumber + "\n" + address +
                    "\nAssessedValue = $" + format("%,d",assessedValue);
        String assessmentClassString = "\n"+ assessmentClass;
        String neighborhood = "\n"+ neighbourhood + "\n"+ location;
        return addressAndValue + assessmentClassString + neighborhood;
    }

    /**
     *  this equal method only compares the property assessment account numbers as each account number is unique
     *
     */
    public boolean equals(Object o){
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        PropertyAssessment propertyAssessment = (PropertyAssessment) o;
        return this.accountNumber == propertyAssessment.accountNumber;
    }
    @Override
    public int hashCode(){
        return Objects.hash(this.accountNumber);
    }
}