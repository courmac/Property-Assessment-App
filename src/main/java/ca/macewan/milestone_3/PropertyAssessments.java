/**
 * Student's Name: Orest Dushkevich and Courtney McNeilly
 * Milestone #2
 * CMPT 305 LAB X02L Fall 2021
 * Instructor's Name: Indratmo Indratmo
 *
 * Purpose:
 * This class holds the PropertyAssessment (s) and
 *
 */
package ca.macewan.milestone_3;


import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PropertyAssessments {
    private ArrayList<PropertyAssessment> propertyList = new ArrayList<PropertyAssessment>();


    /**
     * this method opens up a file and will convert each row into a PropertyAssessment and add it to the
     * PropertyAssessments Object this method was called on
     *
     * untested since not part of the spec but in theory if called on a PropertyAssessments object that's not empty
     * it should append the new data
     *
     * if an invalid filename is input will add nothing to the PropertyAssessments object and just return a print
     * statement that says "could not open file".
     *
     * @param fileName  csv filename or filename path
     * @throws IOException
     * @throws CsvValidationException

    public  void csvFileToPropertyAssessments(String fileName) throws Exception {

        try {
            // Create an object of fileReader
            FileReader fileReader = new FileReader(fileName);
            // Create an object of CSVReader
            CSVReader csvReader = new CSVReader(fileReader);

            // used for opencvs to carry over one row into a String array
            String[] nextRecord;

            //skips over headers
            csvReader.readNext();

            // iterates through csv file and appends each line to the Property ArrayList
            while ((nextRecord = csvReader.readNext()) != null) {
                propertyList.add( new PropertyAssessment(nextRecord));
            }


        } catch (Exception e) {

            throw new Exception("Error: can't open file");

        }
    }

     */


    /**
     * calculates statistics for a specific neighbourhood from a non-case-sensitive sting parameter
     * the method creates an empty PropertyAssessments objects then iterates through the propertyList adding any matching
     * PropertyAssessment objects that match the neighbourhoodName
     *
     * passes the newly created PropertyAssessments into the propertyAssessmentStats(true, neighbourhoodName) method
     * to calculate the statistics
     *
     * @param neighbourhoodName string input of for neighbourhood Name, not case-sensitive
     */
    public int[] neighbourhoodStats(String neighbourhoodName){

        PropertyAssessments neighbourhoodPropertyAssessments = this.propertyAssessmentsByNeighbourhood(neighbourhoodName);

        // neighbourhoodStats are calculated by calling  propertyAssessmentStats
        int[] data;
        if (neighbourhoodPropertyAssessments.propertyList.size() != 0) {
            data = neighbourhoodPropertyAssessments.propertyAssessmentStats();
        } else {
            data = null;
        }
        return data;
    }

    /**
     * this method returns a PropertyAssessments object that's filled with PropertyAssessment objects that match neighbourhoodName
     * @param neighbourhoodName the name of the neighbourhood to search for
     * @return
     */
    public PropertyAssessments propertyAssessmentsByNeighbourhood(String neighbourhoodName){

        PropertyAssessments neighbourhoodPropertyAssessments = new PropertyAssessments();
        // loop iterates through all of PropertyAssessment objects in propertyList to find matches,
        // if prop  matches then is added to neighbourhoodPropertyAssessments
        int i =0;
        for (PropertyAssessment prop : propertyList) {
            if (prop.isInNeighbourhood(neighbourhoodName)) {
                neighbourhoodPropertyAssessments.propertyList.add(prop);
                i++;
            }
        }

        return neighbourhoodPropertyAssessments;
    }

    /**
     * calculates statistics for a specific assessment from a non-case-sensitive sting parameter
     * the method creates an empty PropertyAssessments objects then iterates through the propertyList adding any matching
     * PropertyAssessment objects that match the assessmentClassName
     *
     * passes the newly created PropertyAssessments into the propertyAssessmentStats(true, assessmentClassString) method
     * to calculate the statistics
     *
     * @param assessmentClassName string input of for assessment class Name, not case-sensitive
     */
    public int[] assessmentClassStats(String assessmentClassName){

        //PropertyAssessments neighbourhoodPropertyAssessments = new PropertyAssessments();
        PropertyAssessments neighbourhoodPropertyAssessments = this.propertyAssessmentsByAssessmentClass(assessmentClassName);

        // neighbourhoodStats are calculated by calling  propertyAssessmentStats
        if (neighbourhoodPropertyAssessments.propertyList.size() != 0) {
            return neighbourhoodPropertyAssessments.propertyAssessmentStats();
        } else {
            return null;
        }
    }

    /**
     * this method returns a PropertyAssessments object that's filled with PropertyAssessment objects that match assessmentClassName
     * @param assessmentClassName
     * @return
     */
    public PropertyAssessments propertyAssessmentsByAssessmentClass(String assessmentClassName){

        PropertyAssessments neighbourhoodPropertyAssessments = new PropertyAssessments();

        // loop iterates through all of PropertyAssessment objects in propertyList to find matches,
        // if prop  matches then is added to neighbourhoodPropertyAssessments
        for (PropertyAssessment prop : propertyList) {
            if (prop.isAssessmentClass(assessmentClassName)) {
                neighbourhoodPropertyAssessments.propertyList.add(prop);
            }
        }

        return neighbourhoodPropertyAssessments;
    }


    /**
     * this private method calculates the property assessments statistics of any given PropertyAssessments object.
     * the calculation can be used for any statistic
     * may need to create methods later for PropertyAssessment objects so that not accessing the values directly
     *
     */
    public int[] propertyAssessmentStats() {
        // initialize the statistic values, assume no property will be worth more than $2,147,483,647


        int highestAssessedValue = 0, lowestAssessedValue = 2147483000, range = 0;
        long sum = 0;

        // calculate how many records there are
        int records = propertyList.size();

        if (records == 0) return null;

        // List used to calculate mean
        List<Integer> medianList = new ArrayList<Integer>();

        // iterate through the propertyList
        for (PropertyAssessment prop : propertyList) {
            // calculates the lowest and highest assessed number
            if (!prop.isAssessedValueGreaterThan(lowestAssessedValue)) lowestAssessedValue = prop.assessedValue();
            if (prop.isAssessedValueGreaterThan(highestAssessedValue)) highestAssessedValue = prop.assessedValue();

            // used to calculate mean
            sum += prop.assessedValue();

            // used to calculate median
            medianList.add(prop.assessedValue());
        }

        range = highestAssessedValue - lowestAssessedValue;
        // Sort the values first
        Collections.sort(medianList);
        int medianValue;
        if (records % 2 == 1 ){
            // median will be indexed to be the middle number
            medianValue = medianList.get( (int) records / 2);
        } else {
            //
            medianValue = ( medianList.get( (int) records / 2) + medianList.get( (int) ((records / 2) -1))) / 2;
        }

        int mean = (int) (sum/records);
        return new int[]{records, lowestAssessedValue, highestAssessedValue, range, mean, medianValue};
    }

    /**
     * iterates through propertyList to find a matching account number
     * @param accountNum int account number to search for
     */
    public PropertyAssessment findAccount(int accountNum){
        // System.out.println("Find a property assessment by account number: " + accountNum);
        for (PropertyAssessment prop : propertyList) {
            if (prop.accountNumber() == accountNum) {
                return prop;
            }
        }
        return null;
    }

    public void add(PropertyAssessment property){
        this.propertyList.add(property);
    }

    /**
     * combines two PropertyAssessments
     * @param property
     */
    public void combine(PropertyAssessments property){
        this.propertyList.addAll(property.propertyList);

    }



    public ArrayList<PropertyAssessment> getPropertyList() {
        return propertyList;
    }
}


