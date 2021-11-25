/**
 * Student's Name: Orest Dushkevich
 * Milestone #2
 * CMPT 305 LAB X02L Fall 2021
 * Instructor's Name: Indratmo Indratmo
 *
 * Purpose:
 * This Class is the DAO class that will open up and process a csv file
 *
 *
 */
package ca.macewan.milestone_3;

import com.opencsv.CSVReader;

import java.io.FileReader;

public class CsvPropertyAssessmentDAO implements PropertyAssessmentDAO {
    //private PropertyAssessments propertyAssessmentsList = new PropertyAssessments();
    private String source= "Property_Assessment_Data_2021.csv";
    private int getLessValue = 10000;
    private boolean getLess = false;

    private PropertyAssessments propertyAssessmentAll = null;
    //TODO create a constructor

    @Override
    public PropertyAssessment getByAccountNumber(int accountNumber) {
        if (propertyAssessmentAll == null) propertyAssessmentAll=openCvsFile();
        PropertyAssessment property = propertyAssessmentAll.findAccount(accountNumber);
        return property;
    }

    @Override
    public PropertyAssessments getByNeighbourhood(String neighbourhood) {
        if (propertyAssessmentAll == null) propertyAssessmentAll=openCvsFile();
        PropertyAssessments propertyAssessmentsByNeighbourhood = propertyAssessmentAll.propertyAssessmentsByNeighbourhood(neighbourhood);

        return propertyAssessmentsByNeighbourhood;
    }

    @Override
    public PropertyAssessments getByAssessmentClass(String assessmentClass) {
        if (propertyAssessmentAll == null) propertyAssessmentAll=openCvsFile();
        PropertyAssessments propertyAssessmentsByAssessmentClass = propertyAssessmentAll.propertyAssessmentsByAssessmentClass(assessmentClass);

        return propertyAssessmentsByAssessmentClass;

    }


    @Override
    public PropertyAssessments getAll() {
        getLess = false;
        propertyAssessmentAll = null;
        if (propertyAssessmentAll == null) propertyAssessmentAll=openCvsFile();
        return propertyAssessmentAll;
    }

    @Override
    public PropertyAssessments getLess() {
        propertyAssessmentAll = null;
        getLess = true;
        if (propertyAssessmentAll == null) propertyAssessmentAll=openCvsFile();
        return propertyAssessmentAll;
    }

    @Override
    public void changeSource(String source) {
        this.source = source;
    }
    //TODO delete the setter


    private PropertyAssessments openCvsFile(){
        PropertyAssessments propertyAssessmentsList = new PropertyAssessments();
        try {
            // Create an object of fileReader
            FileReader fileReader = new FileReader(source);
            // Create an object of CSVReader
            CSVReader csvReader1 = new CSVReader(fileReader);
            CSVReader csvReader = new CSVReader(fileReader);

            // used for opencvs to carry over one row into a String array
            String[] nextRecord;

            //skips over headers
            csvReader.readNext();


            int i = 0;

            // iterates through csv file and appends each line to the Property ArrayList
            while ((nextRecord = csvReader.readNext()) != null) {
                propertyAssessmentsList.add( new PropertyAssessment(nextRecord));

                //
                if (getLess){
                    i++;
                    if (i >= getLessValue) break;
                }
            }


        } catch (Exception e) {

            //("Error: can't open file");

        }

        return propertyAssessmentsList;
    }


    public void setGetLessValue(int getLessValue) {
        this.getLessValue = getLessValue;
    }
}
